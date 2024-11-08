package com.lumen1024.data.dto

import com.lumen1024.domain.data.TimeRange

data class TimeRangeDto(
    val start: InstantDto = InstantDto(),
    val end: InstantDto = InstantDto(),
)

fun TimeRange.toTimeRangeDto() = TimeRangeDto(
    start = start.toInstantDto(),
    end = end.toInstantDto()
)

fun TimeRangeDto.toTimeRange() = TimeRange(
    start = start.toInstant(),
    end = end.toInstant()
)