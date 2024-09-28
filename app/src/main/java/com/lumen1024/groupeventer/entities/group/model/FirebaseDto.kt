package com.lumen1024.groupeventer.entities.group.model

import androidx.core.net.toUri
import com.lumen1024.domain.Event
import com.lumen1024.domain.GroupEventStatus
import com.lumen1024.domain.TimeRange
import com.lumen1024.domain.UserData
import com.lumen1024.groupeventer.shared.lib.durationToString
import com.lumen1024.groupeventer.shared.lib.stringToDuration
import java.time.Instant

//-------------------------Group-------------------------

data class GroupDto(
    val id: String = "",
    val name: String = "",
    val color: com.lumen1024.domain.GroupColor = com.lumen1024.domain.GroupColor.RED,
    val description: String = "",
    val password: String = "",

    val events: List<EventDto> = emptyList(),
    val members: Map<String, MemberDataDto> = emptyMap(),
    val admin: String = "",
)

fun GroupDto.toGroup() = com.lumen1024.domain.Group(
    id = id,
    name = name,
    color = color,
    description = description,
    password = password,
    events = events.map { it.toGroupEvent() },
    members = members.map { it.key to it.value.toMemberData() }.toMap(),
    admin = admin
)

fun com.lumen1024.domain.Group.toGroupDto() = GroupDto(
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

fun MemberDataDto.toMemberData() = com.lumen1024.domain.MemberData(
    notificationIds = notificationIds
)

fun com.lumen1024.domain.MemberData.toMemberDataDto() = MemberDataDto(
    notificationIds = notificationIds
)

//-------------------------Event-------------------------

data class EventDto(
    val id: String = "",
    val creator: String = "",

    val status: com.lumen1024.domain.GroupEventStatus = com.lumen1024.domain.GroupEventStatus.Voting,
    val name: String = "",
    val description: String = "",
    val duration: String = "",

    val initialRange: TimeRangeDto = TimeRangeDto(), // range when event might be
    val proposalRanges: Map<String, TimeRangeDto> = emptyMap(), // ranges that people want
    val startTime: Long = Instant.now().toEpochMilli(), // time when event starts
)

fun EventDto.toGroupEvent() = com.lumen1024.domain.Event(
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

fun com.lumen1024.domain.Event.toGroupEventDto() = EventDto(
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

fun TimeRangeDto.toTimeRange() = com.lumen1024.domain.TimeRange(this.start, this.end)

fun com.lumen1024.domain.TimeRange.toTimeRangeDto() = TimeRangeDto(
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

fun UserDataDto.toUserData() = com.lumen1024.domain.UserData(
    id = id,
    name = name,
    avatarUrl = avatarUrl?.toUri(),
    groups = groups,
)

fun com.lumen1024.domain.UserData.toUserDataDto() =
    UserDataDto(
        id = id,
        name = name,
        avatarUrl = avatarUrl?.toString(),
        groups = groups
    )
