package com.lumen1024.groupeventer.entities.group.model

import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import java.util.UUID

data class Group(
    val id: String = UUID.randomUUID().toString(),

    val name: String = "",
    val color: String = "#EEEEEE",
    val description: String = "",
    val password: String = "",

    val events: List<GroupEvent> = emptyList(),
    val people: List<String> = emptyList(),
    val admin: String = ""
)
