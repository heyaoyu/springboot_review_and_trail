package org.heyaoyu.tutorials.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        private val SDF = SimpleDateFormat("yyyy-MM-dd")

        fun dateStrToTs(dateStr: String): Long {
            return SDF.parse(dateStr).time
        }

        fun tsToDateStr(ts: Long): String {
            return SDF.format(Date(ts))
        }
    }
}