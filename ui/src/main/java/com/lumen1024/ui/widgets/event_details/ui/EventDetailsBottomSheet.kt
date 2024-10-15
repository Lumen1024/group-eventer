package com.lumen1024.ui.widgets.event_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.FromGroupText
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
        // Sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // title
                FromGroupText(pair.second)
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Outlined.Edit, null)
                }
            }
            // ranges
            Spacer(Modifier.height(200.dp))

            // footer
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
                    .height(56.dp)
                    .padding(16.dp, 8.dp)
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
                        .fillMaxWidth(),
                    timeRange = TimeRange(),
                    duration = Duration.ofHours(1),
                    onChange = {}
                )
            }
        }

    }
}

@Composable
fun UserRange(modifier: Modifier = Modifier) {

}

@Composable
private fun ded(pair: Pair<Event, Group>) {
    if (pair.first.status == GroupEventStatus.Voting) {
        var timeRange by remember {
            mutableStateOf(
                TimeRange(
                    Instant.now(),
                    Instant.now().plusSeconds(4 * 60 * 60)
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = {},
                shape = RoundedCornerShape(4.dp)
            ) {
                Icon(Icons.Rounded.Done, null)
            }
            TimeRangeSlider(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                timeRange = timeRange,
                duration = Duration.ofHours(1),
                onChange = {}
            )
        }

    }
}

@Composable
fun VotedPeopleRanges(modifier: Modifier = Modifier) {

}

