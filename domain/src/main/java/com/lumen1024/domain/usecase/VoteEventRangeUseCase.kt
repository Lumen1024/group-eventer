package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.repository.EventRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class VoteEventRangeUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val authService: AuthService,
) {

    suspend operator fun invoke(
        eventId: String,
        groupId: String,
        timeRange: TimeRange
    ): Result<Unit> {
        val event = eventRepository.getEventById(eventId, groupId).first()
            ?: return Result.failure(Exception("Event not found"))
        val userId = authService.getCurrentUserId().first()
            ?: return Result.failure(Exception("User not found"))

        return eventRepository.updateEvent(
            groupId = groupId,
            eventId = eventId,
            data = mapOf<String, Any>(
                Event::proposalRanges.name to (event.proposalRanges + (userId to timeRange)),
            )
        )

    }
}