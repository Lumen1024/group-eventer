package com.lumen1024.domain.service

import com.lumen1024.domain.data.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val isUserAuthorized: Flow<Boolean>
    fun getCurrentUser(): Flow<User?>
    fun getCurrentUserId(): Flow<String?>

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
}
