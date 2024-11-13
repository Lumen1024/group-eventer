package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveUserFromGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(groupId: String, userId: String): Result<Unit> {
        val user = userRepository.getUserById(userId).first()
            ?: return Result.failure(Exception("User not found"))
        val group = groupRepository.getGroupById(groupId).first()
            ?: return Result.failure(Exception("Group not found"))

        groupRepository.updateGroup(
            groupId, mapOf(
                Group::members.name to (group.members - userId)
            )
        )

        userRepository.updateUser(
            userId, mapOf(
                User::groups.name to (user.groups - groupId)
            )
        )

        return Result.success(Unit)
    }
}