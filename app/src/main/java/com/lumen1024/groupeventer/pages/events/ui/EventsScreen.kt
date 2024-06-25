package com.lumen1024.groupeventer.pages.events.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group_event.ui.GroupEventCard
import com.lumen1024.groupeventer.pages.events.model.EventsViewModel

@Composable
fun EventsScreen(viewModel: EventsViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.value.eventList, key = {event -> event.id})
        {
            GroupEventCard(event = it)
        }
    }
}

