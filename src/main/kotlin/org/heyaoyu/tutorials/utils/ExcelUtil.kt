package org.heyaoyu.tutorials.utils

import com.alibaba.excel.ExcelWriter
import com.alibaba.excel.metadata.Sheet
import com.alibaba.excel.support.ExcelTypeEnum
import java.io.FileOutputStream

class ExcelUtil {

    companion object {
        fun generateExcel(path: String, content: List<List<String>>, sheet: Int = 0) {
            val fos = FileOutputStream(path)
            val excelWriter = ExcelWriter(fos, ExcelTypeEnum.XLSX)
            excelWriter.write0(content, Sheet(sheet))
            excelWriter.finish()
        }
    }

}