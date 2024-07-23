package com.lumen1024.groupeventer.entities.user_data.model

interface UserDataRepository {

    suspend fun add(userData: UserData) : Result<Unit>

    suspend fun get(id: String): Result<UserData>

    suspend fun update(id: String, data: Map<String, Any>): Result<Unit>

    fun listen(
        id: String,
        callback: (UserData?) -> Unit,
    ): Result<Unit>
}