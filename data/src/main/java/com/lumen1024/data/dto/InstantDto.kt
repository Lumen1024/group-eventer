package com.lumen1024.data.dto

import java.time.Instant

data class InstantDto(
    val epochSeconds: Long = 0,
    val nano: Int = 0,
)

fun Instant.toInstantDto() = InstantDto(this.epochSecond, this.nano)

fun InstantDto.toInstant() = Instant.ofEpochSecond(epochSeconds, nano.toLong())