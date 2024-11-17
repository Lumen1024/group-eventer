package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, password: String, color: GroupColor): Result<Unit> {
        val user = authService.getCurrentUser().first()
            ?: return Result.failure(Exception("User not found"))
        val group = Group(
            id = UUID.randomUUID().toString(),
            name = name,
            password = password,
            color = color,
            members = mapOf(user.id to GroupRole.Admin)
        )

        groupRepository.addGroup(group)
            .onFailure { return Result.failure(it) }
        userRepository.updateUser(user.id, mapOf(User::groups.name to user.groups + group.id))
            .onFailure { return Result.failure(it) }

        return Result.success(Unit)

    }

}