package com.lumen1024.domain

enum class GroupColor(val color: Color) {
    RED(Color(0xFFE53935)),
    PINK(Color(0xFFD81B60)),
    DEEP_PURPLE(Color(0xFF5E35B1)),
    LIGHT_BLUE(Color(0xFF039BE5)),
    TEAL(Color(0xFF00897B)),
    GREEN(Color(0xFF43A047)),
    YELLOW(Color(0xFFFFB300)),
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