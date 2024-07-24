package com.lumen1024.groupeventer.entities.event.model

import com.lumen1024.groupeventer.entities.group.model.TimeRangeDto
import com.lumen1024.groupeventer.entities.group.model.toTimeRangeDto
import com.lumen1024.groupeventer.shared.model.TimeRange

data class EventResponse(
    val agreement: Boolean = false,
    val range: TimeRange = TimeRange(),
)

fun EventResponse.toGroupEventResponseDto(): EventResponseDto {
    return EventResponseDto(this.agreement, this.range.toTimeRangeDto())
}

data class EventResponseDto(
    val agreement: Boolean = false,
    val range: TimeRangeDto = TimeRangeDto(),
)

fun EventResponseDto.toGroupEventResponse(): EventResponse {
    return EventResponse(this.agreement, this.range.toTimeRange())
}