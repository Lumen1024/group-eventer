package com.lumen1024.data

import androidx.core.net.toUri
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.MemberData
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.data.User
import com.lumen1024.domain.tools.durationToString
import com.lumen1024.domain.tools.stringToDuration
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
    id = id,
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

    val status: GroupEventStatus = GroupEventStatus.Voting,
    val name: String = "",
    val description: String = "",
    val duration: String = "",

    val initialRange: TimeRangeDto = TimeRangeDto(), // range when event might be
    val proposalRanges: Map<String, TimeRangeDto> = emptyMap(), // ranges that people want
    val startTime: Long = Instant.now().toEpochMilli(), // time when event starts
)

fun EventDto.toGroupEvent() = Event(
    id = id,
    creator = creator,

    status = status,
    name = name,
    description = description,
    duration = stringToDuration(duration),

    initialRange = initialRange.toTimeRange(),
    proposalRanges = proposalRanges.entries.associate { it.key to it.value.toTimeRange() },
    startTime = Instant.ofEpochMilli(startTime)
)

fun Event.toGroupEventDto() = EventDto(
    id = id,
    creator = creator,

    status = status,
    name = name,
    description = description,
    duration = durationToString(duration),

    initialRange = initialRange.toTimeRangeDto(),
    proposalRanges = proposalRanges.entries.associate { it.key to it.value.toTimeRangeDto() },
    startTime = startTime.toEpochMilli(),
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

fun UserDataDto.toUserData() = User(
    id = id,
    name = name,
    avatarUrl = avatarUrl?.toUri().toString(),
    groups = groups,
)

fun User.toUserDataDto() =
    UserDataDto(
        id = id,
        name = name,
        avatarUrl = avatarUrl?.toString(),
        groups = groups
    )
