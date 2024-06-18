package com.lumen1024.groupeventer.screen.home.events

import com.lumen1024.groupeventer.data.FriendGroup
import com.lumen1024.groupeventer.data.events.GroupEvent

data class EventsUIState(
    val currentGroup: FriendGroup,
    val eventList: List<GroupEvent> = emptyList()
)