package com.lumen1024.groupeventer.entities.group.model

import com.google.firebase.firestore.DocumentId
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent

data class Group(
    @DocumentId
    val id: String? = null,

    val events: List<GroupEvent> = emptyList(),
    val people: List<Long> = emptyList()
)
