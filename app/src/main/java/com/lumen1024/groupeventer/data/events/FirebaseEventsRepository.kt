package com.lumen1024.groupeventer.data.events

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class FirebaseEventsRepository @Inject constructor(
    firebaseApp: Firebase
) : EventsRepository {
    override fun getEvents(groupId: Int) {
        TODO("Not yet implemented")
    }

    override fun addEvent(groupId: Int, event: GroupEvent) {
    }
}