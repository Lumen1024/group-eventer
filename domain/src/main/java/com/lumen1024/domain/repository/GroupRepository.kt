package com.lumen1024.domain.repository

import com.lumen1024.domain.FlowList
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.RepositoryObjectChange
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroupById(id: String): Flow<Group?>
    suspend fun getGroupByCredentials(name: String, password: String? = null): Flow<Group?>
    suspend fun getGroupsByIds(groupIds: List<String> = emptyList()): FlowList<Group>

    suspend fun addGroup(group: Group): Result<Unit>
    suspend fun deleteGroup(groupId: String): Result<Unit>
    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit>

    fun listenChanges(
        groupIds: List<String>,
        callback: (List<RepositoryObjectChange<Group>>) -> Unit,
    ): Result<() -> Unit>

    fun listenChanges(
        groupId: String,
        callback: (Group) -> Unit,
    ): Result<() -> Unit>
}