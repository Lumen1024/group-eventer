package com.lumen1024.groupeventer.entities.group_event.model

import com.lumen1024.groupeventer.entities.group.model.Group

interface GroupRepository {
    suspend fun getGroup(groupId: String): Group

    fun listenGroupChanges(groupId: String, callback: (Group) -> Unit)

    fun addEvent(groupId: String, event: GroupEvent)

    fun removeEvent(groupId: String, event: GroupEvent)

    fun updateEvent(groupId: String, event: GroupEvent)
}