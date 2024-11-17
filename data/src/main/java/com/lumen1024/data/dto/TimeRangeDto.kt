package com.lumen1024.data.dto

import com.lumen1024.domain.data.TimeRange

internal data class TimeRangeDto(
    val start: InstantDto = InstantDto(),
    val end: InstantDto = InstantDto(),
)

internal fun TimeRange.toTimeRangeDto() = TimeRangeDto(
    start = start.toInstantDto(),
    end = end.toInstantDto()
)

internal fun TimeRangeDto.toTimeRange() = TimeRange(
    start = start.toInstant(),
    end = end.toInstant()
)