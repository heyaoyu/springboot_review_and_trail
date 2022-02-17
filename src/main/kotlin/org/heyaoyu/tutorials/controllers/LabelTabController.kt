package org.heyaoyu.tutorials.controllers

import org.heyaoyu.tutorials.entities.LabelTabConfig
import org.heyaoyu.tutorials.entities.LabelTabConfiguration
import org.heyaoyu.tutorials.entities.mongo.CustomLabelTabRule
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/labelTab")
class LabelTabController(val mongoTemplate: MongoTemplate) {

    private fun mergeLabelTabConfig(config: LabelTabConfiguration): List<LabelTabConfig> {
        val defaultMap = LabelTabConfiguration.defaultConfiguration().associateBy { it.type }
        val configMap = config.labelTabConfigs.associateBy { it.type }
        return defaultMap.plus(configMap).values.sortedBy { it.seq }
    }

    private fun getDefaultLabelTabConfigMap(): Map<String, LabelTabConfig> {
        return LabelTabConfiguration.defaultConfiguration().associateBy { it.type }
    }

    @RequestMapping("/tabConfigs")
    @ResponseBody
    fun tabConfigs(@RequestParam("labelId") labelId: String): Any? {
        val config = mongoTemplate.findOne(Query(Criteria.where("labelId").`is`(labelId)), LabelTabConfiguration::class.java)
        val defaultTabConfiguration = LabelTabConfiguration.defaultConfiguration()
        if (config?.labelTabConfigs?.isEmpty() == false) {
            return LabelTabConfiguration(labelId, mergeLabelTabConfig(config))
        }
        return LabelTabConfiguration(labelId, defaultTabConfiguration)
    }

    @RequestMapping("/modifyCommonTab")
    @ResponseBody
    fun modifyCommonTab(
        @RequestParam("labelId") labelId: String,
        @RequestParam("type") type: String,
        @RequestParam("name") name: String,
        @RequestParam("seq") seq: Int,
        @RequestParam("showType") showType: Int,
        @RequestParam("operator") operator: String
    ): Any? {
        val config = mongoTemplate.findOne(Query(Criteria.where("labelId").`is`(labelId)), LabelTabConfiguration::class.java)
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val tabConfig = config.labelTabConfigs.find { it.type == type }
            if (tabConfig != null) {
                tabConfig.name = name
                tabConfig.seq = seq
                tabConfig.showType = showType
                tabConfig.operator = operator
                tabConfig.ts = System.currentTimeMillis()
            } else {
                val newList = ArrayList<LabelTabConfig>()
                newList.addAll(config.labelTabConfigs)
                newList.add(LabelTabConfig(type, name, seq, showType, 1, operator, System.currentTimeMillis(), -1, null))
                config.labelTabConfigs = newList
            }
            mongoTemplate.updateFirst(
                Query(Criteria.where("labelId").`is`(labelId)),
                Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                LabelTabConfiguration::class.java
            )
            return "Ok"
        } else {
            val newConfig = LabelTabConfiguration(
                labelId, listOf(LabelTabConfig(type, name, seq, showType, 1, operator, System.currentTimeMillis(), -1, null))
            )
            mongoTemplate.save(newConfig)
            return "Ok"
        }
    }

    @RequestMapping("/modifyCommonTabStatus")
    @ResponseBody
    fun modifyCommonTabStatus(
        @RequestParam("labelId") labelId: String,
        @RequestParam("type") type: String,
        @RequestParam("status") status: Int,
        @RequestParam("operator") operator: String
    ): Any? {
        val config = mongoTemplate.findOne(Query(Criteria.where("labelId").`is`(labelId)), LabelTabConfiguration::class.java)
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val tabConfig = config.labelTabConfigs.find { it.type == type }
            if (tabConfig != null) {
                tabConfig.status = status
                tabConfig.operator = operator
                tabConfig.ts = System.currentTimeMillis()
            } else {
                val newList = ArrayList<LabelTabConfig>()
                newList.addAll(config.labelTabConfigs)
                val defaultMap = getDefaultLabelTabConfigMap()
                val name = defaultMap[type]?.name ?: throw Exception()
                val seq = defaultMap[type]?.seq ?: throw Exception()
                val showType = defaultMap[type]?.showType ?: throw Exception()
                newList.add(LabelTabConfig(type, name, seq, showType, status, operator, System.currentTimeMillis(), -1, null))
                config.labelTabConfigs = newList
            }
            mongoTemplate.updateFirst(
                Query(Criteria.where("labelId").`is`(labelId)),
                Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                LabelTabConfiguration::class.java
            )
            return "Ok"
        } else {
            val defaultMap = getDefaultLabelTabConfigMap()
            val name = defaultMap[type]?.name ?: throw Exception()
            val seq = defaultMap[type]?.seq ?: throw Exception()
            val showType = defaultMap[type]?.showType ?: throw Exception()
            val newConfig = LabelTabConfiguration(
                labelId, listOf(LabelTabConfig(type, name, seq, showType, status, operator, System.currentTimeMillis(), -1, null))
            )
            mongoTemplate.save(newConfig)
            return "Ok"
        }
    }

    @RequestMapping("/modifyCustomTab")
    @ResponseBody
    fun modifyCustomTab(
        @RequestParam("labelId") labelId: String,
        @RequestParam("srcName") srcName: String,
        @RequestParam("targetName") targetName: String,
        @RequestParam("seq") seq: Int,
        @RequestParam("showType") showType: Int,
        @RequestParam("operator") operator: String
    ): Any? {
        val config = mongoTemplate.findOne(
            Query(
                Criteria.where("labelId").`is`(labelId)
            ), LabelTabConfiguration::class.java
        )
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val tabConfig = config.labelTabConfigs.find { it.type == "自定义" && it.name == srcName }
            if (tabConfig != null) {
                tabConfig.name = targetName
                tabConfig.seq = seq
                tabConfig.showType = showType
                tabConfig.operator = operator
                tabConfig.ts = System.currentTimeMillis()
                mongoTemplate.updateFirst(
                    Query(Criteria.where("labelId").`is`(labelId)),
                    Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                    LabelTabConfiguration::class.java
                )
                return "Ok"
            }
        }
        return "Ok"
    }

    @RequestMapping("/modifyCustomTabStatus")
    @ResponseBody
    fun modifyCustomTabStatus(
        @RequestParam("labelId") labelId: String,
        @RequestParam("srcName") srcName: String,
        @RequestParam("status") status: Int,
        @RequestParam("operator") operator: String
    ): Any? {
        val config = mongoTemplate.findOne(
            Query(
                Criteria.where("labelId").`is`(labelId)
            ), LabelTabConfiguration::class.java
        )
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val tabConfig = config.labelTabConfigs.find { it.type == "自定义" && it.name == srcName }
            if (tabConfig != null) {
                tabConfig.status = status
                tabConfig.operator = operator
                tabConfig.ts = System.currentTimeMillis()
                mongoTemplate.updateFirst(
                    Query(Criteria.where("labelId").`is`(labelId)),
                    Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                    LabelTabConfiguration::class.java
                )
                return "Ok"
            }
        }
        return "Ok"
    }

    @RequestMapping("/addCustomTabAndRule")
    @ResponseBody
    fun addCustomTabAndRule(
        @RequestParam("labelId") labelId: String,
        @RequestParam("name") name: String,
        @RequestParam("showType") showType: Int,
        @RequestParam("sortType") sortType: Int,
        @RequestParam("operator") operator: String,
        @RequestBody rule: CustomLabelTabRule
    ): Any? {
        mongoTemplate.save(rule)
        val config = mongoTemplate.findOne(Query(Criteria.where("labelId").`is`(labelId)), LabelTabConfiguration::class.java)
        val count = config?.labelTabConfigs?.count { it.type == "自定义" } ?: 0
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val newList = ArrayList<LabelTabConfig>()
            newList.addAll(config.labelTabConfigs)
            newList.add(
                LabelTabConfig(
                    "自定义",
                    name,
                    6 + count,
                    showType,
                    0,
                    operator,
                    System.currentTimeMillis(),
                    sortType,
                    rule.id
                )
            )
            config.labelTabConfigs = newList
            mongoTemplate.updateFirst(
                Query(Criteria.where("labelId").`is`(labelId)),
                Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                LabelTabConfiguration::class.java
            )
            return "Ok"
        } else {
            val newConfig = LabelTabConfiguration(
                labelId,
                listOf(
                    LabelTabConfig(
                        "自定义",
                        name,
                        6 + count,
                        showType,
                        0,
                        operator,
                        System.currentTimeMillis(),
                        sortType,
                        rule.id
                    )
                )
            )
            mongoTemplate.save(newConfig)
        }
        return "Ok"
    }

    @RequestMapping("/modifyCustomTabRule")
    @ResponseBody
    fun modifyCustomTabAndRule(
        @RequestParam("labelId") labelId: String,
        @RequestParam("name") name: String,
        @RequestParam("operator") operator: String,
        @RequestBody rule: CustomLabelTabRule
    ): Any? {
        val config = mongoTemplate.findOne(
            Query(
                Criteria.where("labelId").`is`(labelId)
            ), LabelTabConfiguration::class.java
        )
        if (config?.labelTabConfigs?.isEmpty() == false) {
            val tabConfig = config.labelTabConfigs.find { it.type == "自定义" && it.name == name }
            if (tabConfig?.ruleId != null) {
                mongoTemplate.updateFirst(
                    Query(Criteria.where("_id").`is`(tabConfig.ruleId)),
                    Update().set("labelScope", rule.labelScope).set("keywords", rule.keywords).set("postType", rule.postType)
                        .set("contentPool", rule.contentPool).set("score", rule.score).set("allowedUserIds", rule.allowedUserIds),
                    CustomLabelTabRule::class.java
                )
                tabConfig.operator = operator
                tabConfig.ts = System.currentTimeMillis()
                mongoTemplate.updateFirst(
                    Query(Criteria.where("labelId").`is`(labelId)),
                    Update().set("labelId", labelId).set("labelTabConfigs", config.labelTabConfigs),
                    LabelTabConfiguration::class.java
                )
            }
        }
        return "Ok"
    }
}