package org.heyaoyu.tutorials

import org.heyaoyu.tutorials.utils.DateUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        MongoAutoConfiguration::class,
        MongoDataAutoConfiguration::class,
        DataSourceAutoConfiguration::class
    ]
)
class DemoApplication

fun main() {
    println(DateUtil.dateStrToTs("2021-10-01"))
    println(DateUtil.tsToDateStr(1635782400000))
    runApplication<DemoApplication>()
}