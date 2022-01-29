package org.heyaoyu.tutorials.controllers

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class RedisController(@Qualifier("getRedisTemplate") val redisTemplate: RedisTemplate<Any, Any>) {

    @RequestMapping("/demo")
    @ResponseBody
    fun findOne(): Any? {
        println("con:${(redisTemplate.connectionFactory as LettuceConnectionFactory).hostName}")
        println("con:${(redisTemplate.connectionFactory as LettuceConnectionFactory).database}")
        println("con:${(redisTemplate.connectionFactory as LettuceConnectionFactory).port}")
        println("con:${redisTemplate.keySerializer}")
        println("con:${redisTemplate.valueSerializer}")
        return redisTemplate.opsForValue().get("key")
    }
}