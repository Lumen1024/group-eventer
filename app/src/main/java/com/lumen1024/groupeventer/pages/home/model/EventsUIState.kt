package com.lumen1024.groupeventer.pages.home.model

import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent

val mockEvents = listOf(
    GroupEvent(
        name = "Dirta 2",
        creator = "4214-u3u1-3214-kdaw",
        description = "Жёстко сосать",
    ),
    GroupEvent(name = "ded2"),
)

data class EventsUIState(
    val currentGroup: Group = Group(),
    val eventList: List<GroupEvent> = mockEvents
)