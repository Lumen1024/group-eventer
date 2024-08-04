package com.lumen1024.groupeventer.widgets.event_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.widgets.event_details.model.EventDetailsBottomSheetViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismiss: () -> Unit,
    event: Event,
    viewModel: EventDetailsBottomSheetViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { return@rememberModalBottomSheetState true }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(391.dp)
                .background(Color.Yellow)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .background(Color.Red)
        )
    }
}