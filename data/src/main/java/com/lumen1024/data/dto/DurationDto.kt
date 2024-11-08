package com.lumen1024.data.dto

import java.time.Duration

data class DurationDto(
    val seconds: Long = 0,
    val nano: Int = 0,
)

fun Duration.toDurationDto() = DurationDto(this.seconds, this.nano)

fun DurationDto.toDuration() = Duration.ofSeconds(seconds, nano.toLong())