package de.honoka.demo.spring.oauth2.auth.security

import cn.hutool.cache.impl.TimedCache
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 在存在自行保存的登录态的情况下，手动为SecurityContextHolder的context添加authentication信息
 */
object CustomLoginStatusFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        request.getHeader(LoginIdCache.HEADER)?.let {
            if(LoginIdCache.cache.containsKey(it)) {
                /*
                 * 这里必须使用三个参数的UsernamePasswordAuthenticationToken构造方法，因为两个参数的构造方法会
                 * 将对象中的authenticated字段设为false，而三个参数的构造方法会设为true。
                 */
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    it, null, null
                )
            }
        }
        filterChain.doFilter(request, response)
    }
}

object LoginIdCache {

    const val HEADER = "X-Login-ID"

    const val TIMEOUT = 20 * 1000L

    val cache = TimedCache<String, String?>(TIMEOUT).apply {
        schedulePrune(TIMEOUT + 1000L)
    }
}
