package de.honoka.bossddmonitor.common

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.exceptions.ExceptionUtil
import cn.hutool.json.JSONUtil
import de.honoka.bossddmonitor.service.BrowserService
import de.honoka.qqrobot.framework.ExtendedRobotFramework
import de.honoka.qqrobot.framework.api.message.RobotMessage
import de.honoka.qqrobot.framework.api.message.RobotMultipartMessage
import de.honoka.qqrobot.starter.component.ExceptionReporter
import de.honoka.sdk.util.kotlin.basic.cast
import de.honoka.sdk.util.kotlin.basic.log
import de.honoka.sdk.util.kotlin.concurrent.ScheduledTask
import de.honoka.sdk.util.various.ImageUtils
import org.openqa.selenium.WebDriverException
import org.springframework.stereotype.Component
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicInteger

@Component
class ExtendedExceptionReporter(
    private val exceptionReporter: ExceptionReporter,
    private val robotFramework: ExtendedRobotFramework
) {
    
    private data class ExceptionCounts(
        
        val waitForResponseTimeout: AtomicInteger = AtomicInteger(0),
        
        val onErrorPage: AtomicInteger = AtomicInteger(0),

        val tunnelConnectionFailed: AtomicInteger = AtomicInteger(0)
    )
    
    private val scheduledTask = ScheduledTask("1h", "10m", action = ::doTask)
    
    private var counts = ExceptionCounts()
    
    init {
        scheduledTask.startup()
    }
    
    private fun doTask() {
        val map = BeanUtil.beanToMap(counts).apply {
            if(values.all { it.cast<AtomicInteger>().get() < 1 }) return
        }
        val json = JSONUtil.toJsonPrettyStr(map)
        counts = ExceptionCounts()
        robotFramework.sendMsgToDevelopingGroup(RobotMultipartMessage().apply {
            add(RobotMessage.text("过去1小时内受计数异常产生次数："))
            add(RobotMessage.image(ImageUtils.textToImageByLength(json, 50)))
        })
    }

    fun report(t: Throwable) {
        val cause = ExceptionUtil.getRootCause(t)
        val blocked = when(cause) {
            is TimeoutException -> checkException(cause)
            is BrowserService.OnErrorPageException -> {
                counts.onErrorPage.incrementAndGet()
                true
            }
            is WebDriverException -> checkException(cause)
            else -> false
        }
        if(blocked) {
            log.error("", cause)
        } else {
            exceptionReporter.report(cause)
        }
    }
    
    private fun checkException(e: TimeoutException): Boolean = run {
        val stacktrace = ExceptionUtil.stacktraceToString(e)
        when {
            stacktrace.contains("BrowserService.waitForResponse") -> {
                counts.waitForResponseTimeout.incrementAndGet()
                true
            }
            else -> false
        }
    }

    private fun checkException(e: WebDriverException): Boolean = run {
        val stacktrace = ExceptionUtil.stacktraceToString(e)
        when {
            stacktrace.contains("ERR_TUNNEL_CONNECTION_FAILED") -> {
                counts.tunnelConnectionFailed.incrementAndGet()
                true
            }
            else -> false
        }
    }
}
