package com.lumen1024.ui.screen.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.tools.flatMapLinked
import com.lumen1024.ui.widgets.event_details.ui.EventDetailsBottomSheet


@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
) {
    val groups by viewModel.userStateHolder.groups.collectAsState()

    val events by remember(groups) { derivedStateOf { groups.flatMapLinked { it.events } } }

    var selectedEvent: Pair<Event, Group>? by remember { mutableStateOf(null) }

    val isSheetOpen by remember { derivedStateOf { selectedEvent != null } }


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

    if (isSheetOpen)
        EventDetailsBottomSheet(
            onDismiss = { selectedEvent = null },
            pair = selectedEvent!!
        )

}

