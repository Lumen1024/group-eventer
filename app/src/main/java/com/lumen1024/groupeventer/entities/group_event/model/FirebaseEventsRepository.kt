package com.lumen1024.groupeventer.entities.group_event.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.lumen1024.groupeventer.entities.group.model.Group
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    private val firebase: Firebase
) : GroupRepository {

    override suspend fun getGroup(groupId: String): Group {
        return firebase.firestore.collection("groups").document(groupId).get().await()
            .toObject<Group>()!!
    }

    override fun listenGroupChanges(groupId: String, callback: (Group) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addEvent(groupId: String, event: GroupEvent) {
    }

    override fun removeEvent(groupId: String, event: GroupEvent) {
        TODO("Not yet implemented")
    }

    override fun updateEvent(groupId: String, event: GroupEvent) {
        TODO("Not yet implemented")
    }
}