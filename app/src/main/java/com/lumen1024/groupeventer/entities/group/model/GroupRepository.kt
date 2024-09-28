package com.lumen1024.groupeventer.entities.group.model

import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange

interface GroupRepository {
    suspend fun add(group: com.lumen1024.domain.Group): Result<Unit>

    suspend fun get(id: String): Result<com.lumen1024.domain.Group>

    suspend fun get(name: String, password: String? = null): Result<com.lumen1024.domain.Group>

    suspend fun getList(ids: List<String> = emptyList()): Result<List<com.lumen1024.domain.Group>>

    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>

    suspend fun remove(id: String): Result<Unit>

    fun listenList(
        ids: List<String>,
        callback: (List<RepositoryObjectChange<com.lumen1024.domain.Group?>>) -> Unit,
    ): Result<() -> Unit>
}