package com.lumen1024.ui.widgets.event_details.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserStateHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Immutable
data class EventDetailsState(
    val groupName: String,
    val groupColor: Color,
    val event: Event,
)

interface EventDetailsActions

@HiltViewModel(assistedFactory = EventDetailsViewModel.EventDetailsViewModelFactory::class)
class EventDetailsViewModel @AssistedInject constructor(
    @Assisted val event: Event,
    @Assisted val group: Group,
    private val userStateHolder: UserStateHolder,
    private val userActions: UserActions,
) : ViewModel() {
    private val _state: MutableStateFlow<EventDetailsState> = MutableStateFlow(
        EventDetailsState(
            groupName = group.name,
            groupColor = Color(group.color.hex),
            event = event,
        )
    )
    val state = _state.asStateFlow()
    val actions = object : EventDetailsActions {

    }

    @AssistedFactory
    interface EventDetailsViewModelFactory {
        fun create(group: Group, event: Event): EventDetailsViewModel
    }

}