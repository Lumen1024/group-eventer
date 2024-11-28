package com.lumen1024.ui.screen.create_event.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.usecase.CreateEventUseCase
import com.lumen1024.domain.usecase.GetCurrentUserGroupsUseCase
import com.lumen1024.ui.lib.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
    private val createEventUseCase: CreateEventUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CreateEventUiState>(CreateEventUiState.Loading)
    val state = _state.asStateFlow()

    fun onAction(action: CreateEventUiAction) {
        when (action) {
            CreateEventUiAction.OnBackClicked -> onBackClicked()
            CreateEventUiAction.OnConfirmClicked -> onConfirmClicked()
            is CreateEventUiAction.OnDescriptionChanged -> onDescriptionChanged(action.description)
            is CreateEventUiAction.OnDurationSliderValueChanged -> onDurationSliderValueChanged(
                action.value
            )

            is CreateEventUiAction.OnGroupSelected -> selectGroup(action.index)
            is CreateEventUiAction.OnNameChanged -> onNameChanged(action.name)
            is CreateEventUiAction.OnStatusSelected -> onStatusSelected(action.index)
            is CreateEventUiAction.OnTimeChanged -> onTimeChanged(action.time)
            is CreateEventUiAction.OnTimeRangeChanged -> onTimeRangeChanged(action.timeRange)
        }
    }

    init {
        viewModelScope.launch {
            val groups = getCurrentUserGroupsUseCase().first()
            _state.value = CreateEventUiState.Success(groups = groups)
        }
    }

    private fun onBackClicked() {
        navigator.back()
    }

    private fun onConfirmClicked() {
        val state = state.value as? CreateEventUiState.Success
            ?: throw Exception("Action on loading state")
        val event = Event(
            name = state.name,
            description = state.description,
            duration = Duration.ofMinutes(state.durationSliderValue.toLong()),
            status = state.statuses[state.selectedStatus],
            startTime = (state.timePickerState as? TimePickerState.Single)?.time,
            initialRange = (state.timePickerState as? TimePickerState.Range)?.timeRange,
        )

        viewModelScope.launch {
            _state.value = CreateEventUiState.Loading
            createEventUseCase(event, state.groups[state.selectedGroup].id)

        }.invokeOnCompletion { navigator.back() }
    }

    private fun selectGroup(index: Int) =
        updateSuccessState { it.copy(selectedGroup = index) }


    private fun onNameChanged(name: String) =
        updateSuccessState { it.copy(name = name) }

    private fun onDescriptionChanged(description: String) =
        updateSuccessState { it.copy(description = description) }

    private fun onDurationSliderValueChanged(value: Float) =
        updateSuccessState { it.copy(durationSliderValue = value) }


    private fun onStatusSelected(index: Int) =
        updateSuccessState { it.copy(selectedStatus = index) }

    private fun onTimeRangeChanged(timeRange: TimeRange) =
        updateSuccessState { it.copy(timePickerState = TimePickerState.Range(timeRange)) }

    private fun onTimeChanged(time: Instant) =
        updateSuccessState { it.copy(timePickerState = TimePickerState.Single(time)) }

    private fun updateSuccessState(
        action: (CreateEventUiState.Success) -> CreateEventUiState.Success
    ) {
        val oldState = state.value as? CreateEventUiState.Success
            ?: throw Exception("Action on loading state")
        _state.value = action(oldState)
    }
}