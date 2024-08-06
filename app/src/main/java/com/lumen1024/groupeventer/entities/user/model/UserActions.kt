package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupColor

interface UserActions {
    suspend fun joinGroup(name: String, password: String): Result<Unit>
    suspend fun createGroup(name: String, password: String, color: GroupColor): Result<Unit>
    suspend fun leaveGroup(id: String): Result<Unit>
    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit>
    suspend fun transferAdministrator(groupId: String, user: UserData): Result<Unit>
    suspend fun removeUserFromGroup(groupId: String, user: UserData): Result<Unit>

    suspend fun createEvent(event: Event, group: Group): Result<Unit>
    suspend fun updateEvent(event: Event): Result<Unit>
    suspend fun deleteEvent(event: Event): Result<Unit>

    suspend fun updateTokenInGroups(token: String): Result<Unit> // TODO: remove
}