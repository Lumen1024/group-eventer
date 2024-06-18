package com.lumen1024.groupeventer.helper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeRangeFormatter() {
    companion object {
        const val PATTERN: String = "dd.mm.yy"
        val FORMATTER = DateTimeFormatter
            .ofPattern(PATTERN)
            .withZone(ZoneId.systemDefault())

        fun format(instant: Instant): String {
            return FORMATTER.format(instant)
        }
    }
}