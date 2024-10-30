package com.lumen1024.ui.widgets.event_details.ui

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.FromGroupText
import com.lumen1024.ui.shared.Avatar
import com.lumen1024.ui.shared.ScalableBottomSheet
import com.lumen1024.ui.shared.time.TimeRangeSlider
import com.lumen1024.ui.widgets.event_details.model.EventDetailsActions
import com.lumen1024.ui.widgets.event_details.model.EventDetailsState
import java.time.Duration

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismissRequest: () -> Unit,
    state: EventDetailsState,
    actions: EventDetailsActions,
) {

    ScalableBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.statusBarsPadding(),
    ) { heightProgressFraction ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 371.dp)
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
                repeat(3) {
                    item {
                        UserRange(
                            avatar = null,
                            timeRange = state.event.initialRange,
                            duration = state.event.duration,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        )
                    }
                }
            }
            HorizontalDivider()
            Footer(
                initialRange = state.event.initialRange,
                duration = state.event.duration,
                onClick = {},
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = onClick,
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onSecondary
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
            onChange = {},
            step = Duration.ofMinutes(15)
        )
    }
}

@Composable
private fun UserRange(
    avatar: Uri?,
    timeRange: TimeRange,
    duration: Duration,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    modifier: Modifier = Modifier,
) {
    val ratio = remember {
        derivedStateOf { duration.toMillis().toFloat() / timeRange.duration.toMillis() }
    }.value // TODO: hz

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
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor)
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
