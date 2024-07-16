package com.lumen1024.groupeventer.shared.model

import com.google.firebase.firestore.Exclude
import java.time.Duration
import java.time.Instant

data class TimeRange(
    var start: Instant = Instant.now(),
    var end: Instant = Instant.now().plusSeconds(3600),
) {
    constructor() : this(Instant.now(), Instant.now().plusSeconds(3600))

    constructor(start: String, end: String) : this(Instant.parse(start), Instant.parse(end))

    constructor(start: Long, end: Long) : this(
        Instant.ofEpochMilli(start),
        Instant.ofEpochMilli(end)
    )

    @get:Exclude
    val duration: Duration
        get() = Duration.between(
            this.start,
            this.end
        )
}

fun TimeRange.toTimeRangeDto(): TimeRangeDto {
    return TimeRangeDto(this.start.toEpochMilli(), this.end.toEpochMilli())
}

data class TimeRangeDto(
    var start: Long = Instant.now().toEpochMilli(),
    var end: Long = Instant.now().plusSeconds(3600).toEpochMilli(),
)

fun TimeRangeDto.toTimeRange(): TimeRange {
    return TimeRange(this.start, this.end)
}

