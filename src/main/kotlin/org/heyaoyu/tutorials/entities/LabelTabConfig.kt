package org.heyaoyu.tutorials.entities

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

data class LabelTabConfig(
    val type: String,
    var name: String,
    var seq: Int,
    var showType: Int,// 单双
    var status: Int,
    var operator: String,
    var ts: Long,
    var sortType: Int,// 时间，赞，吸引力
    var ruleId: ObjectId?
)

@Document(collection = "labelTabConfigurations")
data class LabelTabConfiguration(val labelId: String, var labelTabConfigs: List<LabelTabConfig>) {
    companion object {
        fun defaultConfiguration(): List<LabelTabConfig> {
            return listOf(
                LabelTabConfig("推荐", "推荐", 1, 2, 1, "", -1, -1, -1),
                LabelTabConfig("最热", "最热", 2, 2, 1, "", -1, -1, -1),
                LabelTabConfig("最新", "最新", 3, 2, 1, "", -1, -1, -1),
                LabelTabConfig("精选", "精选", 4, 2, 1, "", -1, -1, -1),
                LabelTabConfig("作者大大", "作者大大", 5, 2, 1, "", -1, -1, -1)
            )

        }
    }
}