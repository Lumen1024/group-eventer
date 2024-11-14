package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.VoteRequirement
import com.lumen1024.domain.repository.EventRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetCurrentUserEventVoteRequirement @Inject constructor(
    private val eventRepository: EventRepository,
    private val authService: AuthService,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(eventId: String, groupId: String): Flow<VoteRequirement> {
        return authService.getCurrentUserId().flatMapLatest { userId ->
            if (userId == null) return@flatMapLatest flowOf(VoteRequirement.None)

            return@flatMapLatest eventRepository.getEventById(groupId, eventId).transform { event ->
                if (event == null) {
                    emit(VoteRequirement.None)
                    return@transform
                }

                val isVoting = event.status == GroupEventStatus.Voting
                val alreadyVoted = event.proposalRanges.keys.contains(userId)

                val requirement =
                    if (isVoting && !alreadyVoted) {
                        VoteRequirement.VoteProposalRange
                    } else if (userId == event.creator && isVoting) {
                        VoteRequirement.VoteConfirmRange
                    } else {
                        VoteRequirement.None
                    }
                emit(requirement)
            }
        }
    }
}