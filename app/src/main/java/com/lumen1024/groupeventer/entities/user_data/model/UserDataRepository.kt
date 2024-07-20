package com.lumen1024.groupeventer.entities.user_data.model

interface UserDataRepository {

    suspend fun get(userId: String): Result<UserData>

    suspend fun update(userId: String, data: Map<String, Any>): Result<Unit>

    suspend fun add(userData: UserData) : Result<Unit>

    fun listen(
        userId: String,
        callback: (UserData?) -> Unit,
    ): Result<Unit>
}