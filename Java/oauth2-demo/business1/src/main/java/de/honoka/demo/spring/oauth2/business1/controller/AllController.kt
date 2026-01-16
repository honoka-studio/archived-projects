package de.honoka.demo.spring.oauth2.business1.controller

import cn.hutool.json.JSONUtil
import de.honoka.demo.spring.oauth2.business1.service.business2.AllService
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AllController(private val allService: AllService) {

    @GetMapping("/res1")
    fun res1(): ApiResponse<*> = ApiResponse.success("res1")

    @GetMapping("/res2")
    fun res2(): ApiResponse<*> = ApiResponse.success(JSONUtil.parseObj(allService.res1()))
}
