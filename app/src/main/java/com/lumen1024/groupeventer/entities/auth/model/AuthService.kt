package com.lumen1024.groupeventer.entities.auth.model

interface AuthService {

    fun checkAuthorized(): Boolean

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(
        email: String,
        name: String,
        password: String,
    ): Result<Unit>

    suspend fun logout()
}


