package com.lumen1024.groupeventer.entities.auth.model

import android.net.Uri
import com.lumen1024.groupeventer.entities.user.model.User

interface AuthService {

    fun checkAuthorized(): Boolean

    suspend fun updateUser(name: String? = null, avatarUrl: Uri? = null): Result<Unit>

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(
        email: String,
        name: String,
        password: String,
    ): Result<Unit>

    suspend fun logout()

    fun listenChanges(callback: (User?) -> Unit): () -> Unit
}


