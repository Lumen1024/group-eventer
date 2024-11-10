package com.lumen1024.domain.service

interface AvatarService {
    suspend fun updateAvatar(userId: String, imageURI: String): Result<Unit>
}