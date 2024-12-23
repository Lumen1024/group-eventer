package com.lumen1024.domain.service

import com.lumen1024.domain.data.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun getUserAuthorizedState(): Flow<Boolean>
    fun getCurrentUserId(): Flow<String?>
    fun getCurrentUser(): Flow<User?>

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<String>
    suspend fun logout(): Result<Unit>
}
