package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.EventDto
import com.lumen1024.data.dto.toEvent
import com.lumen1024.data.dto.toEventDto
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.repository.EventRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val EVENTS_COLLECTION = "events"

class FirebaseEventRepository @Inject constructor(
    firebase: Firebase,
) : EventRepository {
    val collection = firebase.firestore.collection("groups")

    override suspend fun getEventsByGroupId(groupId: String): Result<List<Event>> {
        val query = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .get()

        return tryCatchDerived("Failed get events by group id") {
            val events = query.await().toObjects(EventDto::class.java).map { it.toEvent() }
            return@tryCatchDerived events
        }
    }

    override suspend fun getEventById(
        groupId: String,
        eventId: String
    ): Result<Event> {
        val query = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .document(eventId)
            .get()

        return tryCatchDerived<Event>("Failed get event by id") {
            val event = query.await().toObject(EventDto::class.java)?.toEvent()
                ?: throw Exception("Event not found")
            return@tryCatchDerived event
        }
    }

    override suspend fun addEvent(
        groupId: String,
        event: Event
    ): Result<Unit> {
        val task = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .add(event.toEventDto())

        return tryCatchDerived<Unit>("Failed delete event") { task.await() }
    }

    override suspend fun updateEvent(
        groupId: String,
        eventId: String,
        data: Map<String, Any>
    ): Result<Unit> {
        val task = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .document(eventId)
            .update(data)

        return tryCatchDerived<Unit>("Failed delete event") { task.await() }
    }

    override suspend fun deleteEvent(
        groupId: String,
        eventId: String
    ): Result<Unit> {
        val task = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .document(eventId)
            .delete()

        return tryCatchDerived<Unit>("Failed delete event") { task.await() }
    }
}