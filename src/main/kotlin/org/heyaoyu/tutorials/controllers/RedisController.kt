package org.heyaoyu.tutorials.controllers

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class RedisController(val redisTemplate: RedisTemplate<Any, Any>) {

    @RequestMapping("/demo")
    @ResponseBody
    fun findOne(): Any {
        val redis = redisTemplate.opsForValue().get("key") as Int?
        val ret = redis ?: 0
        return ret
    }
}