package com.lumen1024.ui.widgets.event_details.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.FromGroupText
import com.lumen1024.ui.shared.Avatar
import com.lumen1024.ui.shared.ScalableBottomSheet
import com.lumen1024.ui.shared.time.TimeRangeSlider
import com.lumen1024.ui.widgets.event_details.model.EventDetailsActions
import com.lumen1024.ui.widgets.event_details.model.EventDetailsState
import com.lumen1024.ui.widgets.event_details.model.TimeSliderUiStyle
import java.time.Duration

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    state: EventDetailsState,
    actions: EventDetailsActions,
) {
    Log.d("ded", state.event.proposalRanges.toString())

    ScalableBottomSheet(
        onDismissRequest = actions::onDismissRequest,
        modifier = Modifier.statusBarsPadding(),
    ) { heightProgressFraction, minHeight ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .fillMaxHeight(heightProgressFraction)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Header(
                groupName = state.groupName,
                groupColor = state.groupColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(state.event.proposalRanges.toList()) {
                    UserRange(
                        avatar = null,
                        initialRange = state.event.initialRange,
                        votedTimeRange = it.second,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    )
                }
            }
            HorizontalDivider()
            Footer(
                initialRange = state.event.initialRange,
                duration = state.event.duration,
                onConfirmClick = actions::onConfirmRange,
                onRangeChange = actions::onRangeChange,
                timeSliderState = state.timeSliderState,
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
    groupName: String,
    groupColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // title
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FromGroupText(groupName, groupColor)
            Text("Name", style = MaterialTheme.typography.titleLarge)
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
    initialRange: TimeRange,
    duration: Duration,
    onConfirmClick: () -> Unit,
    onRangeChange: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
    timeSliderState: TimeSliderUiStyle,
) {
    if (timeSliderState != TimeSliderUiStyle.Gone) {
        val mainColor = if (timeSliderState == TimeSliderUiStyle.Proposal)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.tertiary

        Row(
            modifier = modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = onConfirmClick,
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = mainColor,
                    contentColor = contentColorFor(mainColor)
                )
            ) {
                Icon(Icons.Rounded.Done, null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            TimeRangeSlider(
                modifier = Modifier
                    .fillMaxSize(),
                initialRange = initialRange,
                duration = duration,
                onChange = onRangeChange,
                step = Duration.ofMinutes(15),
                indicatorColor = mainColor
            )
        }
    }
}

@Composable
private fun UserRange(
    avatar: Uri?,
    initialRange: TimeRange,
    votedTimeRange: TimeRange,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    modifier: Modifier = Modifier,
) {
    val ratio by remember {
        derivedStateOf {
            votedTimeRange.duration.toMillis() / initialRange.duration.toMillis().toFloat()
        }
    }

    val timeOffset by remember {
        derivedStateOf { Duration.between(initialRange.start, votedTimeRange.start) }
    }
    val offsetRatio by remember {
        derivedStateOf { timeOffset.toMillis().toFloat() / initialRange.duration.toMillis() }
    }

    var containerWidth by remember { mutableIntStateOf(0) }
    val offset by remember { derivedStateOf { (containerWidth * offsetRatio).toInt() } }

    Row(
        modifier = modifier.padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Avatar(
            modifier = Modifier.size(40.dp),
            url = avatar
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset()
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor)
                .onGloballyPositioned {
                    containerWidth = it.size.width
                }
                .offset(x = with(LocalDensity.current) { offset.toDp() })
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(ratio)
                    .clip(RoundedCornerShape(4.dp))
                    .background(indicatorColor)
            )
        }
    }

}

@Preview
@Composable
private fun EventDetailsPreview() {

}
