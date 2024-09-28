package com.lumen1024.domain

import java.time.Duration
import java.time.Instant

data class TimeRange(
    var start: Instant = Instant.now(),
    var end: Instant = Instant.now().plusSeconds(DEFAULT_RANGE),
) {
    constructor() : this(Instant.now(), Instant.now().plusSeconds(DEFAULT_RANGE))

    constructor(start: Long, end: Long) : this(
        Instant.ofEpochMilli(start),
        Instant.ofEpochMilli(end)
    )

    val duration: Duration
        get() = Duration.between(
            this.start,
            this.end
        )

    override fun toString() = if (start isSameDayAs end) {
        val day = start.getRelativeDayName()
        val startStr = start.toStringWithPattern(pattern = "HH:mm")
        val endStr = end.toStringWithPattern(pattern = "HH:mm")
        "$day $startStr — $endStr"
    } else {
        val startStr = start.toStringWithPattern(pattern = "dd MMM HH:mm")
        val endStr = end.toStringWithPattern(pattern = "dd MMM HH:mm")
        "$startStr — $endStr"
    }

}