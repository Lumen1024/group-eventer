package com.lumen1024.groupeventer.pages.home.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupException
import com.lumen1024.groupeventer.entities.group.model.mapGroupExceptionToMessage
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
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
                    toast(context.resources.getString(exception.mapGroupExceptionToMessage()))
                }
                return@launch
            }

            _uiState.value = uiState.value.copy(
                eventList = groups.getOrNull()!!.flatMap { group -> group.events })
        }
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}