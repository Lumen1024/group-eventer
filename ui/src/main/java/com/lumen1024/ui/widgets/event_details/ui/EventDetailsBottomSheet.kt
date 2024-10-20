package com.lumen1024.ui.widgets.event_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.FromGroupText
import com.lumen1024.ui.shared.time.TimeRangeSlider
import com.lumen1024.ui.widgets.event_details.model.EventDetailsBottomSheetViewModel
import java.time.Duration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismiss: () -> Unit,
    pair: Pair<Event, Group>,
    viewModel: EventDetailsBottomSheetViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { true }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Header(
                group = pair.second,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            HorizontalDivider()
            Spacer(
                modifier = Modifier.height(200.dp)
            )
            HorizontalDivider()
            Footer(
                initialRange = pair.first.initialRange,
                duration = pair.first.duration,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(16.dp, 0.dp, 16.dp, 8.dp)
            )
        }

    }
}

@Composable
private fun Header(
    group: Group,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // title
        Column {
            FromGroupText(group)
            Text("Name")
        }
        IconButton(
            onClick = {}
        ) {
            Icon(Icons.Outlined.Edit, null)
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    initialRange: TimeRange,
    duration: Duration,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = {},
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Icon(Icons.Rounded.Done, null)
        }
        TimeRangeSlider(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            initialRange = initialRange,
            duration = duration,
            onChange = {},
            step = Duration.ofMinutes(60)
        )
    }
}

@Composable
private fun UserRange(
    timeRange: TimeRange,
    duration: Duration,
    indicatorColor: Color = MaterialTheme.colorScheme.tertiary,
    modifier: Modifier = Modifier,
) {
    remember {
        derivedStateOf { duration.toMillis() / timeRange.duration.toMillis() }
    }
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxSize()
        )
    }
}


@Composable
fun VotedPeopleRanges(modifier: Modifier = Modifier) {

}

