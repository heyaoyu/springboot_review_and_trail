package org.heyaoyu.tutorials.entities.mongo

import org.springframework.data.mongodb.core.mapping.Document

data class Keyword(val content: String, val operator: String, val ts: Long)

data class UserId(val content: String, val operator: String, val ts: Long)

@Document(collection = "rules")
data class CustomLabelTabRule(
    val labelScope: String,
    val keywords: List<Keyword>,
    val postType: String,
    val contentPool: String,
    val score: Double,
    val allowedUserIds: List<UserId>
)
