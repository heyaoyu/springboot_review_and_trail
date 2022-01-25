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
    val SDF = SimpleDateFormat("yyyy-MM-dd")
    println(DateUtil.dateStrToTs("2021-10-01"))
    println(SDF.parse(SDF.format(Date(System.currentTimeMillis()))).time)
    println(DateUtil.tsToDateStr(1635782400000))
    runApplication<DemoApplication>()
}