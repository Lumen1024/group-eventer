package com.lumen1024.domain.repository

import com.lumen1024.domain.data.User

interface UserRepository {
    suspend fun getUserById(id: String): Result<User>
    suspend fun addUser(user: User): Result<Unit>
    suspend fun updateUser(id: String, data: Map<String, Any>): Result<Unit>

    fun listenChanges(
        id: String,
        callback: (User) -> Unit,
    ): Result<() -> Unit>
}