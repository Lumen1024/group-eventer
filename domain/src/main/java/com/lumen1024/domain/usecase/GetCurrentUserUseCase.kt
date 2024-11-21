package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.User
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(): Flow<User?> = authService.getCurrentUser()
}