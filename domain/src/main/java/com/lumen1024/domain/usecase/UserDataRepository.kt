package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.UserData

interface UserDataRepository {

    suspend fun add(userData: UserData): Result<Unit>
    suspend fun get(id: String): Result<UserData>
    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>
    suspend fun uploadAvatar(id: String, imageURI: String): Result<String>

    fun listen(
        id: String,
        callback: (UserData?) -> Unit,
    ): Result<() -> Unit>
}