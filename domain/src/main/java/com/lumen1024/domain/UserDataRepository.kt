package com.lumen1024.domain

import android.net.Uri

interface UserDataRepository {

    suspend fun add(userData: UserData): Result<Unit>

    suspend fun get(id: String): Result<UserData>

    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>

    suspend fun uploadAvatar(id: String, image: Uri): Result<Uri>

    fun listen(
        id: String,
        callback: (UserData?) -> Unit,
    ): Result<() -> Unit>
}