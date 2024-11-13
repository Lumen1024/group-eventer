package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.repository.GroupRepository
import kotlinx.coroutines.flow.first

class TransferGroupAdminUseCase(
    private val groupRepository: GroupRepository,
) {

    suspend operator fun invoke(groupId: String, userId: String): Result<Unit> {
        val group = groupRepository.getGroupById(groupId).first()
            ?: return Result.failure(Exception("Group not found"))

        groupRepository.updateGroup(groupId, mapOf(
            Group::members.name to group.members.map {
                if (it.key == userId)
                    it to GroupRole.Admin
                else
                    it to GroupRole.Member
            }
        ))

        return Result.success(Unit)
    }
}