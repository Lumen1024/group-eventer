package com.lumen1024.domain.repository

import com.lumen1024.domain.data.Event

interface EventRepository {
    suspend fun getEventsByGroupId(groupId: String): Result<List<Event>>
    suspend fun getEventById(groupId: String, eventId: String): Result<Event>

    suspend fun addEvent(groupId: String, event: Event): Result<Unit>
    suspend fun updateEvent(groupId: String, eventId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteEvent(groupId: String, eventId: String): Result<Unit>
}