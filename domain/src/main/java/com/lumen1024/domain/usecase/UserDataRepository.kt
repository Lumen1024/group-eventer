package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.User

interface UserDataRepository {

    suspend fun add(user: User): Result<Unit>
    suspend fun get(id: String): Result<User>
    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>
    suspend fun uploadAvatar(id: String, imageURI: String): Result<String>

    fun listen(
        id: String,
        callback: (User?) -> Unit,
    ): Result<() -> Unit>
}