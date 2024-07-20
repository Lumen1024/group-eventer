package com.lumen1024.groupeventer.entities.group.model

import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange

interface GroupRepository {

    suspend fun add(group: Group) : Result<Unit>


    suspend fun get(name: String, password: String? = null) : Result<Group>
    suspend fun get(id: String) : Result<Group>

    suspend fun getList(properties: Map<String, Any> = emptyMap()): Result<List<Group>>
    suspend fun getList(ids: List<String> = emptyList()): Result<List<Group>>


    suspend fun update(groupId: String, data: Map<String, Any>): Result<Unit>

    suspend fun remove(groupId: String): Result<Unit>


    fun listenList(
        groupIds: List<String>,
        callback: (List<RepositoryObjectChange<Group?>>) -> Unit,
    ): Result<Unit>

    fun listen(groupId: String, callback: (Group) -> Unit) : Result<Unit>
}