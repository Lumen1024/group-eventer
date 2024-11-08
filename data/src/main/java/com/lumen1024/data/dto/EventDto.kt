package com.lumen1024.data.dto

import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.GroupEventStatus

data class EventDto(
    val id: String = "",
    val creator: String = "",

    val name: String = "",
    val status: GroupEventStatus = GroupEventStatus.Voting,
    val description: String = "",
    val duration: DurationDto = DurationDto(),

    val initialRange: TimeRangeDto? = null,
    val proposalRanges: Map<String, TimeRangeDto> = emptyMap(),
    val startTime: InstantDto? = null,
)

fun Event.toGroupEventDto() = EventDto(
    id = id,
    creator = creator,

    status = status,
    name = name,
    description = description,
    duration = duration.toDurationDto(),

    initialRange = initialRange?.toTimeRangeDto(),
    proposalRanges = proposalRanges.entries.associate { it.key to it.value.toTimeRangeDto() },
    startTime = startTime?.toInstantDto(),
)

fun EventDto.toGroupEvent() = Event(
    id = id,
    creator = creator,

    name = name,
    status = status,
    description = description,
    duration = duration.toDuration(),

    initialRange = initialRange?.toTimeRange(),
    proposalRanges = proposalRanges.entries.associate { it.key to it.value.toTimeRange() },
    startTime = startTime?.toInstant()
)

