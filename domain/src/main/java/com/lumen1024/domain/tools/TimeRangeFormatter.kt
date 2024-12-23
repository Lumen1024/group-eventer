package com.lumen1024.domain.tools

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeRangeFormatter {
    companion object {
        private const val DAY_MONTH_PATTERN: String = "dd MMM"
        private val ZONED_DAY_MONTH_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern(DAY_MONTH_PATTERN)
            .withZone(ZoneId.systemDefault())

        private const val TIME_PATTERN: String = "HH:mm"
        private val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern(TIME_PATTERN)
            .withZone(ZoneId.of("UTC"))

        private val ZONED_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern(TIME_PATTERN)
            .withZone(ZoneId.systemDefault())

        private const val DAY_MONTH_TIME_PATTERN: String = "dd MMM HH:mm"
        private val ZONED_DAY_MONTH_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern(DAY_MONTH_TIME_PATTERN)
            .withZone(ZoneId.systemDefault())


        fun formatDayMonthWithZone(instant: Instant): String {
            return ZONED_DAY_MONTH_FORMATTER.format(instant)
        }

        fun formatDayMonthTimeWithZone(instant: Instant): String {
            return ZONED_DAY_MONTH_TIME_FORMATTER.format(instant)
        }

        fun formatTimeWithZone(instant: Instant): String {
            return ZONED_TIME_FORMATTER.format(instant)
        }

        fun formatTime(instant: Instant): String {
            return TIME_FORMATTER.format(instant)
        }
    }
}