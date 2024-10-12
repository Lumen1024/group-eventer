package com.lumen1024.ui.widgets.event_details.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.EventCard
import com.lumen1024.ui.shared.time.TimeRangeSlider
import com.lumen1024.ui.widgets.event_details.model.EventDetailsBottomSheetViewModel
import java.time.Duration
import java.time.Instant


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismiss: () -> Unit,
    pair: Pair<Event, Group>,
    viewModel: EventDetailsBottomSheetViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { return@rememberModalBottomSheetState true }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        EventCard(pair = pair, onOptionClicked = {})
        VotedPeopleRanges()
        var timeRange by remember {
            mutableStateOf(
                TimeRange(
                    Instant.now(),
                    Instant.now().plusSeconds(4 * 60 * 60)
                )
            )
        }
        TimeRangeSlider(
            modifier = Modifier.height(40.dp),
            timeRange = timeRange,
            duration = Duration.ofHours(1),
            onChange = {}
        )
    }
}

@Composable
fun VotedPeopleRanges(modifier: Modifier = Modifier) {

}

