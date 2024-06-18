package com.lumen1024.groupeventer.data

import com.lumen1024.groupeventer.data.events.GroupEvent

data class FriendGroup(
    val events: List<GroupEvent> = emptyList(),
    val people: List<Long> = emptyList()
)
