package org.heyaoyu.tutorials

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.hystrix.HystrixAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        MongoAutoConfiguration::class,
        MongoDataAutoConfiguration::class,
        HystrixAutoConfiguration::class,
    ]
)
// 发布或消费注册
@EnableDiscoveryClient
// 消费者only
@EnableFeignClients
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