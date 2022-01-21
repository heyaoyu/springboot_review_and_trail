package org.heyaoyu.tutorials.controllers

import com.mongodb.BasicDBObject
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.ArrayList

data class Record(public val uid: Long, public val content: String, public val create_at: Long)

data class Result(public val _id: Map<Any, Any>, public val record: List<Record>, public val count: Long)

@RestController
@RequestMapping("/mongo")
class MongoDemoController(private val mongoTemplate: MongoTemplate) {

    val collectionName = "collection"

    @RequestMapping("/demo")
    @ResponseBody
    fun get(): Any {
        val match1 = Aggregation.match(
            Criteria.where("status").`is`(1)
                .andOperator(Criteria.where("create_at").gte(1), Criteria.where("create_at").lte(1742594336747))
        )
        val group = Aggregation.group("uid", "content")
            .addToSet(
                BasicDBObject()
                    .append("uid", "$" + "uid")
                    .append("content", "$" + "content")
                    .append("create_at", "$" + "create_at")
            ).`as`("record")
        val project = Aggregation.project()
            .and("record").`as`("record")
            .and("record").size().`as`("count")
        val match2 = Aggregation.match(Criteria.where("count").gt(1))
        val result =
            mongoTemplate.aggregate(
                Aggregation.newAggregation(match1, group, project, match2),
                collectionName,
                Result::class.java
            )
        val groupsOfRecord = result.mappedResults
        val ret: List<List<Record>> = ArrayList()
        groupsOfRecord.forEach {
            process(it.record).forEach {
                (ret as ArrayList).add(it)
            }
        }
        return ret
    }

    private fun process(listOfRecord: List<Record>): List<List<Record>> {
        val ret: List<List<Record>> = ArrayList()
        val list = listOfRecord.sortedBy { it.create_at }
        for (i in 0..list.size - 2) {
            val j = i + 1
            if ((1000 >= Math.abs(list[j].create_at - list[i].create_at))) {
                (ret as ArrayList).add(listOf(list[i], list[j]))
            }
        }
        return ret
    }

    @RequestMapping("/findOne")
    @ResponseBody
    fun findOne(): Any {
        val ret = mongoTemplate.findOne(Query().limit(1), MutableMap::class.java, collectionName)
        return ret?.toMap() ?: "null"
    }

    @RequestMapping("/add")
    @ResponseBody
    fun add(): Any {
        val objs = listOf<Map<Any, Any>>(
            mapOf<Any, Any>("uid" to 123, "content" to "one", "status" to 1, "create_at" to System.currentTimeMillis() - 3000),
            mapOf<Any, Any>("uid" to 123, "content" to "one", "status" to 1, "create_at" to System.currentTimeMillis() - 1000),
            mapOf<Any, Any>("uid" to 234, "content" to "two", "status" to 1, "create_at" to System.currentTimeMillis()),
            mapOf<Any, Any>("uid" to 234, "content" to "two", "status" to 1, "create_at" to System.currentTimeMillis() + 1000),
            mapOf<Any, Any>("uid" to 234, "content" to "two", "status" to 0, "create_at" to System.currentTimeMillis() + 2000),
            mapOf<Any, Any>("uid" to 345, "content" to "three", "status" to 1, "create_at" to System.currentTimeMillis() + 3000),
        )
        mongoTemplate.insert(objs, collectionName)
        return "success"
    }

    @RequestMapping("/clear")
    @ResponseBody
    fun clear(): Any {
        mongoTemplate.dropCollection(collectionName)
        return "success"
    }
}