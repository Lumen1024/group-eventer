package com.lumen1024.groupeventer.data.events

import com.lumen1024.groupeventer.data.FriendGroup

interface GroupRepository {
    suspend fun getGroup(groupId: String) : FriendGroup
    fun listenGroupChanges(groupId: String, callback: (FriendGroup) -> Unit)

    fun addEvent(groupId: String, event: GroupEvent)

    fun removeEvent(groupId: String, event: GroupEvent)

    fun updateEvent(groupId: String, event: GroupEvent)

}