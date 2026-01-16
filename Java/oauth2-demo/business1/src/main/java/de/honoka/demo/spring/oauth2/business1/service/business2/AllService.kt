package de.honoka.demo.spring.oauth2.business1.service.business2

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "oauth2-demo-business2", path = "/")
interface AllService {

    @GetMapping("/res1")
    fun res1(): String
}
