package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupColor
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent

interface UserActions {
    suspend fun joinGroup(name: String, password: String): Result<Unit>
    suspend fun createGroup(name: String, password: String, color: GroupColor): Result<Unit>
    suspend fun leaveGroup(id: String): Result<Unit>
    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit>

    suspend fun createEvent(event: GroupEvent, group: Group): Result<Unit>
    suspend fun updateEvent(event: GroupEvent): Result<Unit>
    suspend fun deleteEvent(event: GroupEvent): Result<Unit>
}