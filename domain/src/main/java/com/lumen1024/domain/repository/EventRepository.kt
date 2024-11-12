package com.lumen1024.domain.repository

import com.lumen1024.domain.FlowList
import com.lumen1024.domain.data.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEventsByGroupId(groupId: String): FlowList<Event>
    fun getEventById(groupId: String, eventId: String): Flow<Event?>

    suspend fun addEvent(groupId: String, event: Event): Result<Unit>
    suspend fun updateEvent(groupId: String, eventId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteEvent(groupId: String, eventId: String): Result<Unit>
}