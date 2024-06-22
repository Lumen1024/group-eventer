package com.lumen1024.groupeventer.entities.group.model

interface GroupRepository {

    suspend fun addGroup(group: Group)

    suspend fun getGroup(groupId: String): Result<Group>
    suspend fun getGroups(): Result<List<Group>>

    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Void>

    suspend fun removeGroup(groupId: String): Result<Void>

    fun listenGroupChanges(groupId: String, callback: (Group) -> Unit)
}