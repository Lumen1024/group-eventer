package com.lumen1024.groupeventer.entities.group_event.model

import java.time.Instant
import java.time.Duration

data class TimeRange(
    val start: String = Instant.now().toString(),
    val end: String = Instant.now().plusSeconds(3600).toString(),
) {
    val duration: Duration
        get() = Duration.between(
            Instant.parse(this.start),
            Instant.parse(this.end)
        )
}
