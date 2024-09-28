package com.lumen1024.domain

interface AuthService {

    val userId: String?
    fun checkAuthorized() = userId != null

    suspend fun refreshToken(): Result<Unit>

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>

    fun listen(callback: () -> Unit): Result<() -> Unit>
}
