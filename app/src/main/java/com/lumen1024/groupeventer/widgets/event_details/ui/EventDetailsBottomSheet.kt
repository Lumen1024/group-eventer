package com.lumen1024.groupeventer.widgets.event_details.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.entities.event.ui.EventCard
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.widgets.event_details.model.EventDetailsBottomSheetViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismiss: () -> Unit,
    pair: Pair<Event, Group>,
    viewModel: EventDetailsBottomSheetViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { return@rememberModalBottomSheetState true }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        EventCard(pair = pair, onOptionClicked = {})
    }
}