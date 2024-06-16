package com.lumen1024.groupeventer.data

data class FriendGroup(
    val events: List<GroupEvent> = emptyList(),
    val people: List<Long> = emptyList()
)
