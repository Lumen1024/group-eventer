package com.lumen1024.domain

import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange

interface GroupRepository {
    suspend fun add(group: Group): Result<Unit>

    suspend fun get(id: String): Result<Group>

    suspend fun get(name: String, password: String? = null): Result<Group>

    suspend fun getList(ids: List<String> = emptyList()): Result<List<Group>>

    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>

    suspend fun remove(id: String): Result<Unit>

    fun listenList(
        ids: List<String>,
        callback: (List<RepositoryObjectChange<Group?>>) -> Unit,
    ): Result<() -> Unit>
}