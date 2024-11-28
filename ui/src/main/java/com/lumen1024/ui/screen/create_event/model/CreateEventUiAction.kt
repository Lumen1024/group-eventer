package com.lumen1024.ui.screen.create_event.model

import androidx.compose.runtime.Immutable
import com.lumen1024.domain.data.TimeRange
import java.time.Instant

@Immutable
sealed interface CreateEventUiAction {

    data class OnGroupSelected(val index: Int) : CreateEventUiAction
    data class OnNameChanged(val name: String) : CreateEventUiAction
    data class OnDescriptionChanged(val description: String) : CreateEventUiAction

    data class OnDurationSliderValueChanged(val value: Float) : CreateEventUiAction
    data class OnStatusSelected(val index: Int) : CreateEventUiAction

    data class OnTimeRangeChanged(val timeRange: TimeRange) : CreateEventUiAction
    data class OnTimeChanged(val time: Instant) : CreateEventUiAction

    data object OnConfirmClicked : CreateEventUiAction
    data object OnBackClicked : CreateEventUiAction
}