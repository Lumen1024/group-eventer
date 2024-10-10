package com.lumen1024.domain.tools

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun stringToInstantWithPattern(string: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Instant {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    return Instant.from(formatter.parse(string))
}

fun Instant.toStringWithPattern(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
        .withLocale(Locale.getDefault())
    return formatter.format(this)
}

infix fun Instant.isSameDayAs(second: Instant): Boolean {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val dateTime1 = this.atZone(zoneId).toLocalDate()
    val dateTime2 = second.atZone(zoneId).toLocalDate()

    return dateTime1 == dateTime2
}

fun Instant.daysFromToday(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    val today = LocalDate.now(zoneId)
    val date = this.atZone(zoneId).toLocalDate()
    return ChronoUnit.DAYS.between(today, date)
}

fun Instant.getRelativeDayName() = when (this.daysFromToday().toInt()) {
    -2 -> "Позавчера"
    -1 -> "Вчера"
    0 -> "Сегодня"
    1 -> "Завтра"
    2 -> "Послезавтра"
    else -> {
        this.toStringWithPattern("dd MMM")
    }
}

fun Instant.toStringRelative() = this.getRelativeDayName() + this.toStringWithPattern(" HH:mm")


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