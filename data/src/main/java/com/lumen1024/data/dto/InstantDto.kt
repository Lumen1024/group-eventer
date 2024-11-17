package com.lumen1024.data.dto

import java.time.Instant

internal data class InstantDto(
    val epochSeconds: Long = 0,
    val nano: Int = 0,
)

internal fun Instant.toInstantDto() = InstantDto(this.epochSecond, this.nano)

internal fun InstantDto.toInstant() = Instant.ofEpochSecond(epochSeconds, nano.toLong())