package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LeaveGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val transferGroupAdminUseCase: TransferGroupAdminUseCase,
) {
    suspend operator fun invoke(groupId: String): Result<Unit> {
        val user = authService.getCurrentUser().first()
            ?: return Result.failure(Exception("User not found"))
        val group = groupRepository.getGroupById(groupId).first()
            ?: return Result.failure(Exception("Group not found"))

        userRepository.updateUser(
            user.id, mapOf(
                User::groups.name to (user.groups - groupId)
            )
        )

        val isLastUser = group.members.size == 1
        if (isLastUser) {
            groupRepository.deleteGroup(groupId)
            return Result.success(Unit)
        }

        val userIsAdmin = group.members[user.id] == GroupRole.Admin
        if (userIsAdmin) {
            val newAdmin = group.members.firstNotNullOfOrNull {
                if (it.value != GroupRole.Admin) it.key else null
            }
            if (newAdmin != null) {
                transferGroupAdminUseCase(groupId, newAdmin)
            } else {

                groupRepository.updateGroup(
                    groupId, mapOf(
                        Group::members.name to group.members - user.id
                    )
                )
            }

        }
        return Result.success(Unit)
    }
}