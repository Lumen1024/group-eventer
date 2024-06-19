package com.lumen1024.groupeventer.entities.group_event.model

import java.time.Instant
import java.time.Duration

data class TimeRange(
    val start: Instant = Instant.now(),
    val end: Instant = Instant.now().plusSeconds(3600),
) { // todo: if firebase crash move to ext
    val duration: Duration get() = Duration.between(this.start, this.end)
}
