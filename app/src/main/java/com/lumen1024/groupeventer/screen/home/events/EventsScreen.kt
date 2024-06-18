package com.lumen1024.groupeventer.screen.home.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.data.events.GroupEvent
import com.lumen1024.groupeventer.helper.TimeRangeFormatter
import com.lumen1024.groupeventer.helper.quantityStringResource

@Composable
fun EventsScreen(viewModel: EventsViewModel = viewModel()) {
    val state = viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(state.value.eventList)
        {
            GroupEventCard(event = it)
        }

    }
}

@Composable
fun EventCard(event: GroupEvent) {
    val timeFrom = TimeRangeFormatter.format(event.acceptedRange.start)
    val timeTo = TimeRangeFormatter.format(
        event.acceptedRange.end
    )
    val duration = quantityStringResource(id = R.plurals.hour, quantity = 1)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(text = event.name)

                    Text(
                        text = "$timeFrom - $timeTo (${duration})"
                    )
                }
                //Text(text = event.creator)
            }
            Row {
                Text(text = event.description)
            }
        }
    }
}

@Preview(locale = "ru")
@Composable
fun EventCardPreview() {
    EventCard(
        GroupEvent(
            name = "Do 2",
            creator = "4214-u3u1-3214-kdaw",
            description = "Жёстко сосать",
        )
    )
}
