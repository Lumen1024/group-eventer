package com.lumen1024.ui.widgets.event_details.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.data.VoteRequirement
import com.lumen1024.domain.usecase.GetCurrentUserEventVoteRequirement
import com.lumen1024.domain.usecase.SetEventFinalTimeUseCase
import com.lumen1024.domain.usecase.VoteEventRangeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

@Immutable
enum class TimeSliderUiStyle {
    Gone,
    Proposal,
    Finish,
}

@HiltViewModel(assistedFactory = EventDetailsViewModel.Factory::class)
class EventDetailsViewModel @AssistedInject constructor(
    @Assisted val event: Event,
    @Assisted val group: Group,
    @Assisted val onDismissRequest: () -> Unit,
    private val getCurrentUserEventVoteRequirement: GetCurrentUserEventVoteRequirement,
    private val voteEventRangeUseCase: VoteEventRangeUseCase,
    private val setEventFinalTimeUseCase: SetEventFinalTimeUseCase,

    ) : ViewModel(), EventDetailsActions {

    private val _state: MutableStateFlow<EventDetailsState> = MutableStateFlow(
        EventDetailsState(
            groupName = group.name,
            groupColor = Color(group.color.hex),
            event = event,
            timeSliderState = TimeSliderUiStyle.Gone,
            selectiveTimeRange = TimeRange(
                event.initialRange!!.start, // TODO: null check problem
                event.initialRange!!.start + event.duration
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserEventVoteRequirement(event.id, group.id).collect {
                val newUiState = when (it) {
                    VoteRequirement.VoteProposalRange -> TimeSliderUiStyle.Proposal
                    VoteRequirement.VoteConfirmRange -> TimeSliderUiStyle.Finish
                    else -> TimeSliderUiStyle.Gone
                }
                _state.value = _state.value.copy(
                    timeSliderState = newUiState
                )
            }
        }
    }

    override fun onConfirmRange() {
        viewModelScope.launch {
            when (state.value.timeSliderState) {
                TimeSliderUiStyle.Proposal -> voteEventRangeUseCase(
                    event.id,
                    group.id,
                    state.value.selectiveTimeRange
                )

                TimeSliderUiStyle.Finish -> setEventFinalTimeUseCase(
                    event.id,
                    group.id,
                    state.value.selectiveTimeRange.start
                )

                else -> {}
            }
        }
        onDismissRequest()
    }

    override fun onRangeChange(range: TimeRange) {
        _state.value = _state.value.copy(
            selectiveTimeRange = range
        )
    }

    override fun onDismissRequest() = (this@EventDetailsViewModel.onDismissRequest).invoke()

    @AssistedFactory
    interface Factory {
        fun create(group: Group, event: Event, onDismissRequest: () -> Unit): EventDetailsViewModel
    }

}