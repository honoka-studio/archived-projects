package de.honoka.demo.spring.oauth2.auth.controller

import cn.hutool.json.JSONObject
import de.honoka.sdk.util.framework.web.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AllController {

    @GetMapping("/oauth2/consent")
    fun oauth2Consent(request: HttpServletRequest): ApiResponse<*> {
        val json = JSONObject()
        request.queryString.split("&").forEach { p ->
            p.split("=").let {
                json.set(it[0], it[1])
            }
        }
        return ApiResponse.success(json)
    }
}
