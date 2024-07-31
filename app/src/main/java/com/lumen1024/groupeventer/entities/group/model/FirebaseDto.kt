package com.lumen1024.groupeventer.entities.group.model

import androidx.core.net.toUri
import com.lumen1024.groupeventer.entities.comment.model.Comment
import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.entities.event.model.EventResponseDto
import com.lumen1024.groupeventer.entities.event.model.GroupEventStatus
import com.lumen1024.groupeventer.entities.event.model.PeopleStatus
import com.lumen1024.groupeventer.entities.event.model.toGroupEventResponse
import com.lumen1024.groupeventer.entities.event.model.toGroupEventResponseDto
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.shared.model.TimeRange
import java.time.Instant

//-------------------------Group-------------------------

data class GroupDto(
    val id: String = "",
    val name: String = "",
    val color: GroupColor = GroupColor.RED,
    val description: String = "",
    val password: String = "",

    val events: List<EventDto> = emptyList(),
    val members: Map<String, MemberDataDto> = emptyMap(),
    val admin: String = "",
)

fun GroupDto.toGroup() = Group(
    id = id,
    name = name,
    color = color,
    description = description,
    password = password,
    events = events.map { it.toGroupEvent() },
    members = members.map { it.key to it.value.toMemberData() }.toMap(),
    admin = admin
)

fun Group.toGroupDto() = GroupDto(
    name = name,
    color = color,
    description = description,
    password = password,
    events = events.map { it.toGroupEventDto() },
    members = members.map { it.key to it.value.toMemberDataDto() }.toMap(),
    admin = admin
)

//-------------------------MemberData-------------------------

data class MemberDataDto(
    val notificationIds: List<String> = emptyList(),
)

fun MemberDataDto.toMemberData() = MemberData(
    notificationIds = notificationIds
)

fun MemberData.toMemberDataDto() = MemberDataDto(
    notificationIds = notificationIds
)

//-------------------------Event-------------------------

data class EventDto(
    val id: String = "",
    val creator: String = "",

    val status: GroupEventStatus = GroupEventStatus.Prepare,
    val name: String = "Новое событие",
    val description: String = "",

    // List of time ranges on event creation
    val requestedRanges: List<TimeRangeDto> = emptyList(),
    // List of users responses to requested ranges
    val voting: Map<String, EventResponseDto> = emptyMap(),

    val finalRange: TimeRangeDto = TimeRangeDto(),

    val comments: List<Comment> = emptyList(),
    val people: Map<String, PeopleStatus> = emptyMap(),
)

fun EventDto.toGroupEvent() = Event(
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

fun Event.toGroupEventDto() = EventDto(
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

// -------------------------TimeRange-------------------------

data class TimeRangeDto(
    var start: Long = Instant.now().toEpochMilli(),
    var end: Long = Instant.now().plusSeconds(3600).toEpochMilli(),
)

fun TimeRangeDto.toTimeRange() = TimeRange(this.start, this.end)

fun TimeRange.toTimeRangeDto() = TimeRangeDto(
    this.start.toEpochMilli(),
    this.end.toEpochMilli()
)

// -------------------------UserData-------------------------

data class UserDataDto(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val groups: List<String> = emptyList(),
)

fun UserDataDto.toUserData() = UserData(
    id = id,
    name = name,
    avatarUrl = avatarUrl?.toUri(),
    groups = groups,
)

fun UserData.toUserDataDto() =
    UserDataDto(
        id = id,
        name = name,
        avatarUrl = avatarUrl?.toString(),
        groups = groups
    )
