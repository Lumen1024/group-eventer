package com.lumen1024.groupeventer.entities.group.model

import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent

data class Group(
    val id: String = "",

    val events: List<GroupEvent> = emptyList(),
    val people: List<Long> = emptyList()
)
