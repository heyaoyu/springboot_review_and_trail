package org.heyaoyu.tutorials.controllers

import org.heyaoyu.tutorials.jooq.codegen.Tables
import org.heyaoyu.tutorials.jooq.codegen.tables.Category.CATEGORY
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/mysql")
class MysqlDemoController(@Qualifier("dslContext") private val dslContext: DSLContext) {

    @RequestMapping("/findOne")
    @ResponseBody
    fun findOne(): Any {
        val ret = dslContext.fetch(Tables.CATEGORY).toList().get(0)
        return ret.get(CATEGORY.NAME)
    }

    @RequestMapping("/add")
    @ResponseBody
    fun add(): Any {
        dslContext.insertInto(Tables.CATEGORY)
            .set(CATEGORY.NAME, "苹果")
            .set(CATEGORY.STATUS, 1)
            .set(CATEGORY.ORDER, 0)
            .set(CATEGORY.CREATE_TIME, LocalDateTime.now())
            .execute()
        return "OK"
    }

}