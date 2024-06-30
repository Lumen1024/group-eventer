package com.lumen1024.groupeventer.entities.user.model

interface UserRepository {
    suspend fun getData(userId: String): Result<UserData>

    suspend fun updateData(userId: String, data: Map<String, Any>): Result<Void>
}