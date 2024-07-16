package com.lumen1024.groupeventer.entities.group_event.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.entities.comment.model.Comment
import com.lumen1024.groupeventer.shared.model.TimeRange
import com.lumen1024.groupeventer.shared.model.TimeRangeDto
import com.lumen1024.groupeventer.shared.model.toTimeRange
import com.lumen1024.groupeventer.shared.model.toTimeRangeDto

data class GroupEvent(
    val id: String = "",
    val creator: String = "",

    val status: GroupEventStatus = GroupEventStatus.Prepare,
    val name: String = "Новое событие",
    val description: String = "",

    // List of time ranges on event creation
    val requestedRanges: List<TimeRange> = emptyList(),
    // List of users responses to requested ranges
    val voting: Map<String, GroupEventResponse> = emptyMap(),

    val finalRange: TimeRange = TimeRange(),

    val comments: List<Comment> = emptyList(),
    val people: Map<String, PeopleStatus> = emptyMap(),
)

enum class GroupEventStatus {
    Prepare,
    Voting,
    Ended,
}

fun GroupEventStatus.getColorResource() = when (this) {
    GroupEventStatus.Prepare -> R.color.status_prepare
    GroupEventStatus.Voting -> R.color.status_voting
    GroupEventStatus.Ended -> R.color.status_ended
}

fun GroupEventStatus.getIcon() = when (this) {
    GroupEventStatus.Prepare -> Icons.Default.AccessTime
    GroupEventStatus.Voting -> Icons.Default.Groups
    GroupEventStatus.Ended -> Icons.Default.CalendarToday
}

enum class PeopleStatus {
    Ready,
    Refused,
    Unknown,
    Maybe,
}

fun GroupEvent.toGroupEventDto(): GroupEventDto {
    return GroupEventDto(
        id = id,
        creator = creator,
        status = status,
        name = name,
        description = description,
        requestedRanges = requestedRanges.map { it.toTimeRangeDto() },
        voting = voting.entries.associate { it.key to it.value.toGroupEventResponseDto() },
        finalRange = finalRange.toTimeRangeDto(),
        comments = comments,
        people = people
    )
}

data class GroupEventDto(
    val id: String = "",
    val creator: String = "",

    val status: GroupEventStatus = GroupEventStatus.Prepare,
    val name: String = "Новое событие",
    val description: String = "",

    // List of time ranges on event creation
    val requestedRanges: List<TimeRangeDto> = emptyList(),
    // List of users responses to requested ranges
    val voting: Map<String, GroupEventResponseDto> = emptyMap(),

    val finalRange: TimeRangeDto = TimeRangeDto(),

    val comments: List<Comment> = emptyList(),
    val people: Map<String, PeopleStatus> = emptyMap(),
)

fun GroupEventDto.toGroupEvent(): GroupEvent {
    return GroupEvent(
        id = id,
        creator = creator,
        status = status,
        name = name,
        description = description,
        requestedRanges = requestedRanges.map { it.toTimeRange() },
        voting = voting.entries.associate { it.key to it.value.toGroupEventResponse() },
        finalRange = finalRange.toTimeRange(),
        comments = comments,
        people = people
    )
}
