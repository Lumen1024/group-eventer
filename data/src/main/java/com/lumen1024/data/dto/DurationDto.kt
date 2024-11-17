package com.lumen1024.data.dto

import java.time.Duration

internal data class DurationDto(
    val seconds: Long = 0,
    val nano: Int = 0,
)

internal fun Duration.toDurationDto() = DurationDto(this.seconds, this.nano)

internal fun DurationDto.toDuration() = Duration.ofSeconds(seconds, nano.toLong())