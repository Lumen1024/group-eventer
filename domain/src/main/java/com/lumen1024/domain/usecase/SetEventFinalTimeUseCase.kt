package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.repository.EventRepository
import java.time.Instant
import javax.inject.Inject

class SetEventFinalTimeUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {

    suspend operator fun invoke(eventId: String, groupId: String, time: Instant): Result<Unit> {
        return eventRepository.updateEvent(
            eventId,
            groupId,
            mapOf(
                Event::startTime.name to time,
                Event::status.name to GroupEventStatus.Scheduled
            )
        )
    }
}