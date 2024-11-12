package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.EventDto
import com.lumen1024.data.dto.toEvent
import com.lumen1024.data.dto.toEventDto
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.FlowList
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.repository.EventRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val EVENTS_COLLECTION = "events"

class FirebaseEventRepository @Inject constructor(
    firebase: Firebase,
) : EventRepository {
    val collection = firebase.firestore.collection("groups")

    override fun getEventsByGroupId(groupId: String): FlowList<Event> = callbackFlow {
        val query = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            val events = snapshot?.toObjects(EventDto::class.java)?.map { it.toEvent() }
            trySend(events ?: emptyList())
        }
        awaitClose { registration.remove() }
    }

    override fun getEventById(
        groupId: String,
        eventId: String
    ): Flow<Event?> = callbackFlow {
        val query = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .document(eventId)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            val event = snapshot?.toObject(EventDto::class.java)?.toEvent()
            trySend(event)
        }
        awaitClose { registration.remove() }
    }

    override suspend fun addEvent(
        groupId: String,
        event: Event
    ): Result<Unit> {
        val task = collection
            .document(groupId)
            .collection(EVENTS_COLLECTION)
            .add(event.toEventDto())

        return tryCatchDerived("Failed delete event") { task.await() }
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

        return tryCatchDerived("Failed delete event") { task.await() }
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

        return tryCatchDerived("Failed delete event") { task.await() }
    }
}