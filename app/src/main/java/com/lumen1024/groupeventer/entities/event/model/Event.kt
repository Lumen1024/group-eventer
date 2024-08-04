package com.lumen1024.groupeventer.entities.event.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import com.lumen1024.groupeventer.entities.comment.model.Comment
import com.lumen1024.groupeventer.shared.model.TimeRange
import java.util.UUID
import kotlin.time.Duration

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val creator: String = "",

    val status: GroupEventStatus = GroupEventStatus.Prepare,
    val name: String = "Новое событие",
    val description: String = "",
    val duration: Duration = Duration.ZERO, // TODO: DTO

    // List of time ranges on event creation
    val requestedRanges: List<TimeRange> = emptyList(),
    // List of users responses to requested ranges
    val voting: Map<String, EventResponse> = emptyMap(),

    val finalRange: TimeRange = TimeRange(),

    val comments: List<Comment> = emptyList(),
    val people: Map<String, PeopleStatus> = emptyMap(),
)

enum class GroupEventStatus {
    Prepare,
    Voting,
    Ended,
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

