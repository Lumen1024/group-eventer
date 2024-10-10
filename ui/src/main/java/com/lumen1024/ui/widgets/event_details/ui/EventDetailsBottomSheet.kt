package com.lumen1024.ui.widgets.event_details.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.screen.events.EventCard
import com.lumen1024.ui.widgets.event_details.model.EventDetailsBottomSheetViewModel


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
//        TimeRangeSlider()
    }
}

@Composable
fun VotedPeopleRanges(modifier: Modifier = Modifier) {

}

