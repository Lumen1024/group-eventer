package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService
) {

    suspend operator fun invoke(name: String, password: String): Result<Unit> {
        val group = groupRepository.getGroupByCredentials(name, password).first()
            ?: return Result.failure(Exception("Group not found"))

        val user = authService.getCurrentUser().first()
            ?: return Result.failure(Exception("User not found"))

        groupRepository.updateGroup(
            group.id, mapOf(
                Group::members.name to group.members + (user.id to GroupRole.Member)
            )
        )
            .onFailure { return Result.failure(it) }

        userRepository.updateUser(
            user.id, mapOf(
                User::groups.name to user.groups + group.id
            )
        )
            .onFailure { return Result.failure(it) }


        return Result.success(Unit)
    }
}