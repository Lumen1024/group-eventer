package com.lumen1024.domain.data

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val creator: String = "",
    val group: String = "",

    val name: String = "New Event",
    val status: GroupEventStatus = GroupEventStatus.Voting,
    val description: String = "",
    val duration: Duration = Duration.ZERO,

    val initialRange: TimeRange? = null,                     // range when event might be
    val proposalRanges: Map<String, TimeRange> = emptyMap(), // ranges that people want
    val startTime: Instant? = null,                          // time when event starts
)

enum class GroupEventStatus {
    Voting,
    Scheduled,
    Finish,
}
