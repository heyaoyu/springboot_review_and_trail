package org.heyaoyu.tutorials.controllers

import com.alibaba.excel.ExcelReader
import com.alibaba.excel.read.context.AnalysisContext
import com.alibaba.excel.read.event.AnalysisEventListener
import com.alibaba.excel.support.ExcelTypeEnum
import org.heyaoyu.tutorials.process
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileInputStream
import kotlin.contracts.contract

@RestController
@RequestMapping("/excel")
class ExcelController {

    @RequestMapping("/readExcel")
    @ResponseBody
    fun readExcel(): Any {
        val pathFile = File("/Users/heyaoyu/Downloads/full_danmu_dup")
        var counter = 0
        pathFile.list().filter { it.endsWith("xlsx") }.forEach {
            try {
                counter += process(pathFile.path + "/" + it)
            } catch (e: Exception) {
            }
        }
        return counter
    }

    fun process(path: String): Int {
        val ael = object : AnalysisEventListener<List<String>>() {
            var counter = 0

            override fun invoke(strings: List<String>?, ac: AnalysisContext?) {
                strings?.forEach {
                    if ("0".equals(it)) {
                        counter += 1
                    }
                }
            }

            override fun doAfterAllAnalysed(ac: AnalysisContext?) {
                println("$path done with $counter")
            }

        }
        val reader = ExcelReader(FileInputStream(path), ExcelTypeEnum.XLSX, null, ael)
        reader.read()
        return ael.counter
    }
}