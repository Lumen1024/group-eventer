package com.lumen1024.ui.screen.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.widgets.event_details.ui.EventDetailsBottomSheet


@Composable
fun EventsScreen(
    state: EventsScreenState,
    actions: EventScreenActions,
) {
    // TODO: loading animation
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.eventsWithGroups.toList(), key = { pair -> pair.first.id + pair.second.id })
        {
            EventCard(
                pair = it,
                onClick = { actions.onEventClicked(it) },
                onOptionClicked = {}
            )
        }
    }

    if (state.eventDetailsBottomSheetState is EventDetailsBottomSheetState.Opened) {
        EventDetailsBottomSheet(
            event = state.eventDetailsBottomSheetState.event,
            group = state.eventDetailsBottomSheetState.group,
            onDismissRequest = state.eventDetailsBottomSheetState.onDismissRequest
        )
    }
}

@Composable
fun EventsScreen() {
    val viewModel: EventsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val actions: EventScreenActions = viewModel

    EventsScreen(state, actions)
}


@Preview
@Composable
fun EventsScreenPreview() {
    val state by remember { mutableStateOf(EventsScreenState()) }
    val actions: EventScreenActions = object : EventScreenActions {

        override fun onEventClicked(pair: Pair<Event, Group>) {
            TODO("Not yet implemented")
        }
    }

    EventsScreen(state, actions)
}