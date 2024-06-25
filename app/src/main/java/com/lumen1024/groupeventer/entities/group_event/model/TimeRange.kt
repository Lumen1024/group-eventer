package com.lumen1024.groupeventer.entities.group_event.model

import com.google.firebase.firestore.Exclude
import java.time.Duration
import java.time.Instant

data class TimeRange(
    val start: String = Instant.now().toString(),
    val end: String = Instant.now().plusSeconds(3600).toString(),
) {
    @get:Exclude
    val duration: Duration
        get() = Duration.between(
            Instant.parse(this.start),
            Instant.parse(this.end)
        )
}
