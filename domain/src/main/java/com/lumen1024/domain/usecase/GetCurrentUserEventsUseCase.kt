package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetCurrentUserEventsUseCase @Inject constructor(
    private val getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
    private val eventRepository: EventRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Map<Event, Group>> {
        return getCurrentUserGroupsUseCase().flatMapLatest { groups ->
            val flows = groups.map { group ->
                eventRepository.getEventsByGroupId(group.id).transform {
                    emit(group to it)
                }
            }

            val parsedFlows: List<Flow<List<Pair<Event, Group>>>> = flows.map {
                it.transform { pair ->
                    val parsed = pair.second.map { it to pair.first }
                    emit(parsed)
                }
            }

            // combine and emit
            val combined = combine(parsedFlows) { it.flatMap { it } }
            val combinedMap = combined.transform { emit(it.toMap()) }

            return@flatMapLatest combinedMap
        }
    }
}