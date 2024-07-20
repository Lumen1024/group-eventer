package com.lumen1024.groupeventer.entities.auth.model

interface AuthService {

    fun checkAuthorized(): Result<Boolean>

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>

    // maybe listen
}


