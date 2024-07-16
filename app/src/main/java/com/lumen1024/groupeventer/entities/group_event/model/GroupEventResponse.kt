package com.lumen1024.groupeventer.entities.group_event.model

import com.lumen1024.groupeventer.shared.model.TimeRange
import com.lumen1024.groupeventer.shared.model.TimeRangeDto
import com.lumen1024.groupeventer.shared.model.toTimeRange
import com.lumen1024.groupeventer.shared.model.toTimeRangeDto

data class GroupEventResponse(
    val agreement: Boolean = false,
    val range: TimeRange = TimeRange(),
)

fun GroupEventResponse.toGroupEventResponseDto(): GroupEventResponseDto {
    return GroupEventResponseDto(this.agreement, this.range.toTimeRangeDto())
}

data class GroupEventResponseDto(
    val agreement: Boolean = false,
    val range: TimeRangeDto = TimeRangeDto(),
)

fun GroupEventResponseDto.toGroupEventResponse(): GroupEventResponse {
    return GroupEventResponse(this.agreement, this.range.toTimeRange())
}