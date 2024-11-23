package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class ChangeUserAvatarUseCase @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        avatarUrl: String,
    ): Result<Unit> {
        val user = getCurrentUserUseCase().first()
            ?: return Result.failure(Exception("User not found"))

        return userRepository.updateUser(user.id, mapOf(User::avatarUrl.name to avatarUrl))
    }
}