package com.lumen1024.ui.screen.create_event.model

import androidx.compose.runtime.Immutable
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.tools.round
import java.time.Duration
import java.time.Instant

@Immutable
sealed interface CreateEventUiState {
    object Loading : CreateEventUiState
    object Error : CreateEventUiState

    data class Success(
        val groups: List<Group>,
        val selectedGroup: Int = 0,

        val name: String = "",
        val description: String = "",

        val durationHeader: String = "0 h",
        val durationSliderRange: ClosedFloatingPointRange<Float> = 0f..24f,
        val durationSliderValue: Float = 1f,

        val statuses: List<GroupEventStatus> = GroupEventStatus.entries,
        val selectedStatus: Int = 0,

        val timePickerState: TimePickerState = TimePickerState.Single(
            Instant.now().round(Duration.ofHours(1))
        ),
    ) : CreateEventUiState

}

@Immutable
sealed interface TimePickerState {
    data class Range(val timeRange: TimeRange) : TimePickerState
    data class Single(val time: Instant) : TimePickerState
}