package com.lumen1024.ui.screen.events

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.usecase.GetCurrentUserEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class EventsScreenState(
    val eventsWithGroups: Map<Event, Group> = emptyMap(),
    val eventDetailsBottomSheetState: EventDetailsBottomSheetState =
        EventDetailsBottomSheetState.Closed
)

@Immutable
interface EventScreenActions {
    fun onEventClicked(pair: Pair<Event, Group>)
}

@Immutable
sealed interface EventDetailsBottomSheetState {
    object Closed : EventDetailsBottomSheetState
    data class Opened(
        val event: Event,
        val group: Group,
        val onDismissRequest: () -> Unit
    ) : EventDetailsBottomSheetState
}


@HiltViewModel
class EventsViewModel @Inject constructor(
    getCurrentUserEventsUseCase: GetCurrentUserEventsUseCase,
) : ViewModel(), EventScreenActions {

    private val _state = MutableStateFlow(EventsScreenState())
    val state: StateFlow<EventsScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserEventsUseCase().collect {
                _state.value = _state.value.copy(eventsWithGroups = it)
            }
        }
    }

    override fun onEventClicked(pair: Pair<Event, Group>) {
        _state.value = _state.value.copy(
            eventDetailsBottomSheetState = EventDetailsBottomSheetState.Opened(
                event = pair.first,
                group = pair.second,
                onDismissRequest = {
                    _state.value = _state.value.copy(
                        eventDetailsBottomSheetState = EventDetailsBottomSheetState.Closed
                    )
                }
            )
        )
    }
}