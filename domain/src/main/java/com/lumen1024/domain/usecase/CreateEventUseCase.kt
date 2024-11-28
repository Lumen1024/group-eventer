package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Event
import com.lumen1024.domain.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {

    suspend operator fun invoke(event: Event, groupId: String): Result<Unit> {
        return eventRepository.addEvent(groupId, event)
    }
}