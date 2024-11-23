package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.service.AuthService
import com.lumen1024.domain.tools.FlowList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCurrentUserGroupsUseCase @Inject constructor(
    private val authService: AuthService,
    private val groupRepository: GroupRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): FlowList<Group> = flow {
        authService.getCurrentUser().flatMapLatest<User?, List<Group>> { user ->
            if (user == null) return@flatMapLatest flowOf(emptyList())

            groupRepository.getGroupsByIds(user.groups)
        }
    }
}