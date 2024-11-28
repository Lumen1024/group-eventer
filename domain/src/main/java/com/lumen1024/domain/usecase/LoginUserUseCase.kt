package com.lumen1024.domain.usecase

import com.lumen1024.domain.service.AuthService
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authService: AuthService,
) {

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authService.login(email, password)
    }
}