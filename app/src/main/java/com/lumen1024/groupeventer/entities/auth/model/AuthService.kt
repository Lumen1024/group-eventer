package com.lumen1024.groupeventer.entities.auth.model

import com.google.firebase.auth.FirebaseUser

interface AuthService {

    fun checkAuthorized(): Boolean

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun register(
        email: String,
        name: String,
        password: String
    ): FirebaseUser?

    suspend fun logout()
}

