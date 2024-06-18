package com.lumen1024.groupeventer.data.events

import com.lumen1024.groupeventer.data.Comment
import com.lumen1024.groupeventer.data.TimeRange

data class GroupEvent(
    val id: String = "",

    val status: GroupEventStatus = GroupEventStatus.Timing,
    val name: String = "Новое событие",
    val description: String = "",

    val requestedRange: TimeRange = TimeRange(),
    val acceptedRange: TimeRange = TimeRange(),
    val voting: Map<Long, GroupEventResponse> = emptyMap(),

    val creator: String = "",

    val comments: List<Comment> = emptyList(),
    val people: Map<Long, PeopleStatus> = emptyMap(),
)

enum class GroupEventStatus {
    Timing,
    Voting,
    Memory,
}

data class GroupEventResponse(
    val agreement: Boolean = false,
    val range: TimeRange = TimeRange(),
)

enum class PeopleStatus {
    Ready,
    Refused,
    Unknown,
    Maybe,
}