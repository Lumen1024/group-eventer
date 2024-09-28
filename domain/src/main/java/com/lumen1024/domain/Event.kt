package com.lumen1024.domain

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val creator: String = "",

    val status: GroupEventStatus = GroupEventStatus.Voting,
    val name: String = "",
    val description: String = "",
    val duration: Duration = Duration.ZERO,

    val initialRange: TimeRange = TimeRange(), // range when event might be
    val proposalRanges: Map<String, TimeRange> = emptyMap(), // ranges that people want
    val startTime: Instant = Instant.now(), // time when event starts
)

enum class GroupEventStatus {
    Voting,
    Scheduled,
    Finish,
}
