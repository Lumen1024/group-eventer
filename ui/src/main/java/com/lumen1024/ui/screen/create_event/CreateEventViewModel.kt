package com.lumen1024.ui.screen.create_event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserStateHolder
import com.lumen1024.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val userActions: UserActions,
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

        viewModelScope.launch { userActions.createEvent(event, group) }
        navigator.back()
    }

    fun saveEvent(event: Event, group: Group) {
        viewModelScope.launch { userActions.createEvent(event, group) }
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

        viewModelScope.launch { userActions.createEvent(event, group) }
        navigator.back()
    }
}