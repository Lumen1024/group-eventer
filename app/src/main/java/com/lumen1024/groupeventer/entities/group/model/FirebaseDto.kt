package com.lumen1024.groupeventer.entities.group.model

import androidx.core.net.toUri
import com.lumen1024.groupeventer.entities.comment.model.Comment
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventResponseDto
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventStatus
import com.lumen1024.groupeventer.entities.group_event.model.PeopleStatus
import com.lumen1024.groupeventer.entities.group_event.model.toGroupEventResponse
import com.lumen1024.groupeventer.entities.group_event.model.toGroupEventResponseDto
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.shared.model.TimeRange
import java.time.Instant

data class GroupDto(
    val id: String = "",
    val name: String = "",
    val color: GroupColor = GroupColor.RED,
    val description: String = "",
    val password: String = "",

    val events: List<GroupEventDto> = emptyList(),
    val people: List<String> = emptyList(),
    val admin: String = "",
) {
    fun toGroup() = Group(
        id = id,
        name = name,
        color = color,
        description = description,
        password = password,
        events = events.map { it.toGroupEvent() },
        people = people,
        admin = admin
    )
}

fun Group.toGroupDto() = GroupDto(
    name = name,
    color = color,
    description = description,
    password = password,
    events = events.map { it.toGroupEventDto() },
    people = people,
    admin = admin

)


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
) {
    fun toGroupEvent() = GroupEvent(
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

fun GroupEvent.toGroupEventDto() = GroupEventDto(
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

// --------------------------------------------

data class TimeRangeDto(
    var start: Long = Instant.now().toEpochMilli(),
    var end: Long = Instant.now().plusSeconds(3600).toEpochMilli(),
) {
    fun toTimeRange(): TimeRange {
        return TimeRange(this.start, this.end)
    }
}

fun TimeRange.toTimeRangeDto() = TimeRangeDto(
    this.start.toEpochMilli(),
    this.end.toEpochMilli()
)

// --------------------------------------------

data class UserDataDto(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val groups: List<String> = emptyList(),
)

fun UserData.toUserDataDto() =
    UserDataDto(
        id = id,
        name = name,
        avatarUrl = avatarUrl?.toString(),
        groups = groups
    )

fun UserDataDto.toUserData() = UserData(
    id = id,
    name = name,
    avatarUrl = avatarUrl?.toUri(),
    groups = groups,
)