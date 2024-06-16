package com.lumen1024.groupeventer.events

import com.lumen1024.groupeventer.data.GroupEvent

data class EventsUIState(
    val eventList: List<GroupEvent> = emptyList()
)