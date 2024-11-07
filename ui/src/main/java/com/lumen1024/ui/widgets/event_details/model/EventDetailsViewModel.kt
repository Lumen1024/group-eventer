package com.lumen1024.ui.widgets.event_details.model

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserStateHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
enum class TimeSliderUiStyle {
    Gone,
    Proposal,
    Finish,
}

@Immutable
data class EventDetailsState(
    val groupName: String,
    val groupColor: Color,
    val event: Event,
    val timeSliderState: TimeSliderUiStyle,
    val selectiveTimeRange: TimeRange
)

interface EventDetailsActions {
    fun onConfirmRange()
    fun onRangeChange(range: TimeRange)
    fun onDismissRequest()
}

@HiltViewModel(assistedFactory = EventDetailsViewModel.Factory::class)
class EventDetailsViewModel @AssistedInject constructor(
    @Assisted val event: Event,
    @Assisted val group: Group,
    @Assisted val onDismissRequest: () -> Unit,
    private val userStateHolder: UserStateHolder,
    private val userActions: UserActions,
) : ViewModel() {
    var initSliderUiState = TimeSliderUiStyle.Gone // TODO

    init {
        val userId = userStateHolder.userData.value?.id!! // TODO

        val isVoting = event.status == GroupEventStatus.Voting


        var alreadyVote = false
        event.proposalRanges.keys.forEach { if (it == userId) alreadyVote = true }
        Log.d("ded", event.proposalRanges.keys.toString())

        initSliderUiState =
            if (isVoting && !alreadyVote) {
                TimeSliderUiStyle.Proposal
            } else if (userId == event.creator && isVoting) {
                TimeSliderUiStyle.Finish
            } else {
                TimeSliderUiStyle.Gone
            }
    }

    private val _state: MutableStateFlow<EventDetailsState> = MutableStateFlow(
        EventDetailsState(
            groupName = group.name,
            groupColor = Color(group.color.hex),
            event = event,
            timeSliderState = initSliderUiState,
            selectiveTimeRange = TimeRange(
                event.initialRange.start,
                event.initialRange.start + event.duration
            )
        )
    )
    val state = _state.asStateFlow()
    val actions = object : EventDetailsActions {
        override fun onConfirmRange() {
            when (state.value.timeSliderState) {
                TimeSliderUiStyle.Proposal -> {
                    viewModelScope.launch {
                        userActions.voteEventTime(event.id, state.value.selectiveTimeRange)
                            .onFailure { Log.d("ded", it.message!!) }
                    }
                    this@EventDetailsViewModel.onDismissRequest()
                }

                TimeSliderUiStyle.Finish -> {
                    viewModelScope.launch {
                        userActions.setFinalEventTime(
                            event.id,
                            state.value.selectiveTimeRange.start
                        )
                    }
                    this@EventDetailsViewModel.onDismissRequest()
                }

                else -> TODO()
            }
        }

        override fun onRangeChange(range: TimeRange) {
            _state.value = _state.value.copy(
                selectiveTimeRange = range
                //event = event.copy(proposalRanges = state.value.event.proposalRanges + (userStateHolder.userData.value!!.id to range)) // TODO: check
            )
        }

        override fun onDismissRequest() = this@EventDetailsViewModel.onDismissRequest()
    }

    @AssistedFactory
    interface Factory {
        fun create(group: Group, event: Event, onDismissRequest: () -> Unit): EventDetailsViewModel
    }

}