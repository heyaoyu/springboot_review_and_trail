package org.heyaoyu.tutorials.controllers

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mongo")
class MongoDemoController(private val mongoTemplate: MongoTemplate) {

    val collectionName = "collection"

    @RequestMapping("/demo")
    @ResponseBody
    fun get(): Any {
        val ret = mongoTemplate.findOne(Query().limit(1), MutableMap::class.java, collectionName)
        return ret?.toMap() ?: "null"
    }

    @RequestMapping("/add")
    @ResponseBody
    fun add(): Any {
        val obj = mapOf<Any, Any>("name" to "james", "age" to 20)
        mongoTemplate.insert(obj, collectionName)
        return "success"
    }
}