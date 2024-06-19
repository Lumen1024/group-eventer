package com.lumen1024.groupeventer.entities.group_event.model

import com.lumen1024.groupeventer.entities.comment.model.Comment

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

enum class PeopleStatus {
    Ready,
    Refused,
    Unknown,
    Maybe,
}
