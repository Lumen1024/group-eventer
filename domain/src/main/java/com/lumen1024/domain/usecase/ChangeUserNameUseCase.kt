package com.lumen1024.domain.usecase

import com.lumen1024.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChangeUserNameUseCase @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(
        name: String,
    ): Result<Unit> {
        val user = getCurrentUserUseCase().first()
            ?: return Result.failure(exception = Exception("User not found"))
        return userRepository.updateUser(user.id, mapOf("name" to name))

    }
}