package com.lumen1024.groupeventer.data.events

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.lumen1024.groupeventer.data.FriendGroup
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    val firebase: Firebase
) : GroupRepository {

    override suspend fun getGroup(groupId: String) : FriendGroup {
        return firebase.firestore.collection("groups").document(groupId).get().await().toObject<FriendGroup>()!!
    }

    override fun listenGroupChanges(groupId: String, callback: (FriendGroup) -> Unit) {
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