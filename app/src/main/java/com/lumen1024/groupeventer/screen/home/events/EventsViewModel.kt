package com.lumen1024.groupeventer.screen.home.events

import androidx.lifecycle.ViewModel
import com.lumen1024.groupeventer.data.FriendGroup
import com.lumen1024.groupeventer.data.events.GroupEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        EventsUIState(
            currentGroup = FriendGroup(),
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