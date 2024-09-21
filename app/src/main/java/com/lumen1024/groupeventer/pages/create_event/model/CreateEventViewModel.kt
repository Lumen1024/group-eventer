package com.lumen1024.groupeventer.pages.create_event.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.entities.event.model.GroupEventStatus
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserActions
import com.lumen1024.groupeventer.entities.user.model.UserStateHolder
import com.lumen1024.groupeventer.shared.model.Navigator
import com.lumen1024.groupeventer.shared.model.TimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val firebaseUserActions: FirebaseUserActions,
    val userStateHolder: UserStateHolder,
    val navigator: Navigator,
) : ViewModel() {
    fun saveEvent(
        group: Group,
        name: String,
        description: String,
        duration: Float,
        status: GroupEventStatus,
        startTime: Instant,
    ) {
        val event = Event(
            name = name,
            description = description,
            status = status,
            duration = Duration.ofHours(duration.toLong()),
            startTime = startTime
        )

        viewModelScope.launch { firebaseUserActions.createEvent(event, group) }
        navigator.back()
    }

    fun saveEvent(event: Event, group: Group) {
        viewModelScope.launch { firebaseUserActions.createEvent(event, group) }
        navigator.back()
    }

    fun saveEvent(
        group: Group,
        name: String,
        description: String,
        duration: Float,
        status: GroupEventStatus,
        initialRange: TimeRange
    ) {
        val event = Event(
            name = name,
            description = description,
            status = status,
            duration = Duration.ofHours(duration.toLong()),
            initialRange = initialRange
        )

        viewModelScope.launch { firebaseUserActions.createEvent(event, group) }
        navigator.back()
    }
}