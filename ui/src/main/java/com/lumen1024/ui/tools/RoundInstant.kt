package com.lumen1024.ui.tools

import java.time.Duration
import java.time.Instant

fun Instant.round(roundValue: Duration): Instant {
    val instantMillis = this.toEpochMilli()
    val roundValueMillis = roundValue.toMillis()

    val roundedMillis = instantMillis - (instantMillis % roundValueMillis)
    return Instant.ofEpochMilli(roundedMillis)

}