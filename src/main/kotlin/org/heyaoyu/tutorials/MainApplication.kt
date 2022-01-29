package org.heyaoyu.tutorials

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@SpringBootApplication(
    exclude = [
        MongoAutoConfiguration::class,
        MongoDataAutoConfiguration::class,
        DataSourceAutoConfiguration::class
    ]
)
class DemoApplication

fun main() {
    val appCtx = runApplication<DemoApplication>()
    appCtx.getBeansOfType(RedisTemplate::class.java).forEach {
        println(it)
    }
    appCtx.getBeansOfType(RedisConnectionFactory::class.java).forEach {
        println(it)
    }
}