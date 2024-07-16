package com.lumen1024.groupeventer.entities.group.model

import androidx.compose.ui.graphics.Color
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventDto
import com.lumen1024.groupeventer.entities.group_event.model.toGroupEvent
import com.lumen1024.groupeventer.entities.group_event.model.toGroupEventDto
import java.util.UUID

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

    val events: List<GroupEvent> = emptyList(),
    val people: List<String> = emptyList(),
    val admin: String = "",
)

fun Group.toGroupDto(): GroupDto {
    return GroupDto(
        id = id,
        name = name,
        color = color,
        description = description,
        password = password,
        events = events.map { it.toGroupEventDto() },
        people = people,
        admin = admin
    )
}

data class GroupDto(
    val id: String = UUID.randomUUID().toString(),

    val name: String = "",
    val color: GroupColor = GroupColor.RED,
    val description: String = "",
    val password: String = "",

    val events: List<GroupEventDto> = emptyList(),
    val people: List<String> = emptyList(),
    val admin: String = "",
)

fun GroupDto.toGroup(): Group {
    return Group(
        id = id,
        name = name,
        color = color,
        description = description,
        password = password,
        events = events.map { it.toGroupEvent() },
        people = people,
        admin = admin
    )
}