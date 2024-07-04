package com.lumen1024.groupeventer.entities.group.model

import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange

interface GroupRepository {

    suspend fun addGroup(group: Group)

    suspend fun getGroup(groupId: String): Result<Group>
    suspend fun getGroups(groupIds: List<String> = emptyList()): Result<List<Group>>

    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Void>

    suspend fun removeGroup(groupId: String): Result<Void>

    fun listenChanges(
        groupIds: List<String>,
        callback: (List<RepositoryObjectChange<Group?>>) -> Unit,
    ): () -> Unit

    fun listenGroupChanges(groupId: String, callback: (Group) -> Unit)
}