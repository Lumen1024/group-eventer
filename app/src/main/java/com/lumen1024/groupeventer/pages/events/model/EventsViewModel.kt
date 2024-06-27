package com.lumen1024.groupeventer.pages.events.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group.model.GroupException
import com.lumen1024.groupeventer.entities.group.model.mapGroupExceptionToMessage
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val groupRepository: FirebaseGroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        EventsUIState()
    )
    val uiState: StateFlow<EventsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groups = groupRepository.getGroups()

            if (groups.isFailure) {
                val exception = groups.exceptionOrNull()!!;
                if (exception is GroupException) {
                    context.showToast(context.resources.getString(exception.mapGroupExceptionToMessage()))
                }
                return@launch
            }

            _uiState.value = uiState.value.copy(
                eventList = groups.getOrNull()!!.flatMap { group -> group.events })
        }
    }
}