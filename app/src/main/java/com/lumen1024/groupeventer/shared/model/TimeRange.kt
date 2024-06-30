package com.lumen1024.groupeventer.shared.model

import com.google.firebase.firestore.Exclude
import java.time.Duration
import java.time.Instant

data class TimeRange(
    var start: Instant = Instant.now(),
    var end: Instant = Instant.now().plusSeconds(3600),
) {
    constructor(start: String, end: String) : this(Instant.parse(start), Instant.parse(end))

    @get:Exclude
    val duration: Duration
        get() = Duration.between(
            this.start,
            this.end
        )
}
