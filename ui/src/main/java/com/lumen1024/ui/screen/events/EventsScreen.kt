package com.lumen1024.ui.screen.events

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.tools.flatMapLinked
import com.lumen1024.ui.widgets.event_details.model.EventDetailsViewModel
import com.lumen1024.ui.widgets.event_details.ui.EventDetailsBottomSheet


@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
) {
    val groups by viewModel.userStateHolder.groups.collectAsState()

    val events by remember(groups) { derivedStateOf { groups.flatMapLinked { it.events } } }

    var selectedEvent: Pair<Event, Group>? by remember { mutableStateOf(null) }

    val isSheetOpen by remember { derivedStateOf { selectedEvent != null } }

    if (events.isEmpty() || groups.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "There is not events yet")
            if (groups.isEmpty()) {
                Text(text = "Join a group to see events")
            }
        }
    }

    AnimatedVisibility(visible = !groups.isEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events.toList(), key = { pair -> pair.first.id })
            {
                EventCard(
                    pair = it,
                    onClick = { selectedEvent = it },
                    onOptionClicked = {}
                )
            }
        }
    }

    if (isSheetOpen) {
        val viewModel: EventDetailsViewModel = hiltViewModel(
            creationCallback = { factory: EventDetailsViewModel.Factory ->
                selectedEvent?.let {
                    factory.create(it.second, it.first) { selectedEvent = null }
                } ?: throw IllegalArgumentException("selected event is null")
            },
            key = selectedEvent?.second.toString() + selectedEvent?.first.toString() // TODO: is that right?
        )
        val state by viewModel.state.collectAsState()
    }
}