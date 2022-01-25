package org.heyaoyu.tutorials

import org.heyaoyu.tutorials.utils.DateUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import java.text.SimpleDateFormat
import java.util.*

@SpringBootApplication(
    exclude = [
        MongoAutoConfiguration::class,
        MongoDataAutoConfiguration::class,
        DataSourceAutoConfiguration::class,
        RedisAutoConfiguration::class
    ]
)
class DemoApplication

fun main() {
    runApplication<DemoApplication>()
}