package com.lumen1024.domain.repository

import com.lumen1024.domain.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserById(id: String): Flow<User?>
    suspend fun addUser(user: User): Result<Unit>
    suspend fun updateUser(id: String, data: Map<String, Any>): Result<Unit>
}