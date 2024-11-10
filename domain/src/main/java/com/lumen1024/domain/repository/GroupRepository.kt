package com.lumen1024.domain.repository

import com.lumen1024.domain.FlowList
import com.lumen1024.domain.data.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroupById(id: String): Flow<Group?>
    fun getGroupByCredentials(name: String, password: String? = null): Flow<Group?>
    fun getGroupsByIds(groupIds: List<String> = emptyList()): FlowList<Group>

    suspend fun addGroup(group: Group): Result<Unit>
    suspend fun deleteGroup(groupId: String): Result<Unit>
    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit>
}