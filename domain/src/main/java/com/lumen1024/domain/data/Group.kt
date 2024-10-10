package com.lumen1024.domain.data

import java.util.UUID

enum class GroupColor(val hex: Long) {
    RED(0xFFE53935),
    PINK(0xFFD81B60),
    DEEP_PURPLE(0xFF5E35B1),
    LIGHT_BLUE(0xFF039BE5),
    TEAL(0xFF00897B),
    GREEN(0xFF43A047),
    YELLOW(0xFFFFB300),
}

data class Group(
    val id: String = UUID.randomUUID().toString(),

    val name: String = "",
    val color: GroupColor = GroupColor.RED,
    val description: String = "",
    val password: String = "",

    val events: List<Event> = emptyList(),
    val members: Map<String, MemberData> = emptyMap(),
    val admin: String = "",
)

data class MemberData(
    val notificationIds: List<String> = emptyList(),
)