package org.heyaoyu.tutorials.controllers

import org.heyaoyu.tutorials.entities.mongo.CustomLabelTabRule
import org.heyaoyu.tutorials.entities.mongo.Keyword
import org.heyaoyu.tutorials.entities.mongo.UserId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mongoRule")
class MongoRuleController(val mongoTemplate: MongoTemplate) {

    @RequestMapping("/init")
    @ResponseBody
    fun init(): Any {
        val keywords = listOf(
            Keyword("key1", "operator1", System.currentTimeMillis()),
            Keyword("key2", "operator1", System.currentTimeMillis())
        )
        val userIds = listOf(
            UserId("uid1", "operator1", System.currentTimeMillis())
        )
        val rule = CustomLabelTabRule("1,2", keywords, "1,3,5", "1,3", 3.5, userIds)
        mongoTemplate.save(rule)
        return "success"
    }

    @RequestMapping("/findOne")
    @ResponseBody
    fun findOne(): Any {
        val rule = mongoTemplate.findOne(Query().limit(1), CustomLabelTabRule::class.java)
        println(rule)
        return "success"
    }
}