package com.lumen1024.groupeventer.shared.lib

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun stringToInstantWithPattern(string: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Instant {
    val formatter = DateTimeFormatter.ofPattern(pattern)
        .withZone(ZoneId.systemDefault())
    return Instant.from(formatter.parse(string))
}

fun instantToStringWithPattern(instant: Instant, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
        .withZone(ZoneId.systemDefault()).withLocale(Locale.getDefault())
    return formatter.format(instant)
}

fun durationToString(duration: Duration): String {
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return "${hours}h ${minutes}m ${seconds}s"
}

fun durationToTimeString(duration: Duration): String {
    val hours = duration.toHours().toString().padStart(2, '0')
    val minutes = (duration.toMinutes() % 60).toString().padStart(2, '0')

    return "$hours:$minutes"
}

fun stringToDuration(string: String): Duration {
    var hours = 0L
    var minutes = 0L
    var seconds = 0L

    // Regex to find hours, minutes, and seconds
    val hourRegex = """(\d+)h""".toRegex()
    val minuteRegex = """(\d+)m""".toRegex()
    val secondRegex = """(\d+)s""".toRegex()

    hourRegex.find(string)?.let {
        hours = it.groupValues[1].toLong()
    }

    minuteRegex.find(string)?.let {
        minutes = it.groupValues[1].toLong()
    }

    secondRegex.find(string)?.let {
        seconds = it.groupValues[1].toLong()
    }

    return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds)
}