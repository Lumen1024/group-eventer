package com.lumen1024.groupeventer.pages.home.model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        EventsUIState()
    )
    val uiState: StateFlow<EventsUIState> = _uiState.asStateFlow()
}