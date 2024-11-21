package com.lumen1024.domain.service

interface AvatarService {
    suspend fun uploadAvatar(userId: String, imageURI: String): Result<Unit>
}