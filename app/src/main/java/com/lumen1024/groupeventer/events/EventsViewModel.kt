package com.lumen1024.groupeventer.events

import androidx.lifecycle.ViewModel
import com.lumen1024.groupeventer.data.GroupEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        EventsUIState(
            eventList = listOf(
                GroupEvent(
                    name = "Dirta 2",
                    creator = "4214-u3u1-3214-kdaw",
                    description = "Жёстко сосать",
                ),
                GroupEvent(name = "ded2"),
            )
        )
    )
    val uiState: StateFlow<EventsUIState> = _uiState.asStateFlow()


}