package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.tools.FlowMap
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetUsersByGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(group: Group): FlowMap<User, GroupRole> {
        val flows = group.members.map { pair ->
            userRepository.getUserById(pair.key).transform { emit(it to pair.value) }
        }

        val combined: FlowMap<User, GroupRole> = combine(flows) {
            it.filter { it.first != null }.map { it.first!! to it.second }.toMap()
        }

        return combined
    }
}