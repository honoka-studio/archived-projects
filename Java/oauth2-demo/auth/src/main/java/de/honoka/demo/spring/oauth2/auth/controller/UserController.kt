package de.honoka.demo.spring.oauth2.auth.controller

import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import de.honoka.demo.spring.oauth2.auth.entity.User
import de.honoka.demo.spring.oauth2.auth.service.UserService
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.web.bind.annotation.*

@RequestMapping("/user")
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ApiResponse<*> {
        userService.save(user)
        return ApiResponse.success()
    }

    @GetMapping("/info/{name}")
    fun findByName(@PathVariable name: String): ApiResponse<*> {
        val user = userService.getOne(QueryWrapper<User>().run {
            eq("username", name)
        })
        return ApiResponse.success(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody body: JSONObject): ApiResponse<*> = run {
        val res = JSONUtil.parse(userService.login(body))
        ApiResponse.success(res)
    }

    @PostMapping("/loginId")
    fun loginId(@RequestBody body: JSONObject): ApiResponse<*> = run {
        ApiResponse.success(userService.newLoginId(body))
    }
}
