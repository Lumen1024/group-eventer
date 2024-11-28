package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import javax.inject.Inject
import kotlin.Result


class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authService: AuthService
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Unit> {
        val userId = authService.register(email, password).getOrNull()
            ?: return Result.failure(Exception("Failed to register user"))
        val user = User(id = userId, name = name)
        return userRepository.addUser(user)
    }
}