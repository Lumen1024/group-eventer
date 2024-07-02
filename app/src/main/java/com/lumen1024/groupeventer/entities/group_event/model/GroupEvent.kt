package com.lumen1024.groupeventer.entities.group_event.model

import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.entities.comment.model.Comment
import com.lumen1024.groupeventer.shared.model.TimeRange

data class GroupEvent(
    val id: String = "",

    val status: GroupEventStatus = GroupEventStatus.Prepare,
    val name: String = "Новое событие",
    val description: String = "",

    val requestedRange: TimeRange = TimeRange(),
    val acceptedRange: TimeRange = TimeRange(),
    val voting: Map<String, GroupEventResponse> = emptyMap(),

    val creator: String = "",

    val comments: List<Comment> = emptyList(),
    val people: Map<String, PeopleStatus> = emptyMap(),
)

enum class GroupEventStatus {
    Prepare,
    Voting,
    Ended,
}

fun GroupEventStatus.getColorResource() = when(this) {
    GroupEventStatus.Prepare -> R.color.status_prepare
    GroupEventStatus.Voting -> R.color.status_voting
    GroupEventStatus.Ended -> R.color.status_ended
}

enum class PeopleStatus {
    Ready,
    Refused,
    Unknown,
    Maybe,
}
