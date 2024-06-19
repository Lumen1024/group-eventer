package com.lumen1024.groupeventer.shared.lib

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeRangeFormatter {
    companion object {
        private const val PATTERN: String = "dd MMM hh:mm"
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern(PATTERN)
            .withZone(ZoneId.systemDefault())

        fun format(instant: Instant): String {
            return FORMATTER.format(instant)
        }
    }
}