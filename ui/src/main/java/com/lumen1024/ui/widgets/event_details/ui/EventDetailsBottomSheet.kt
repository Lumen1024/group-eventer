package com.lumen1024.ui.widgets.event_details.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.screen.events.FromGroupText
import com.lumen1024.ui.shared.Avatar
import com.lumen1024.ui.shared.time.TimeRangeSlider
import com.lumen1024.ui.widgets.event_details.model.EventDetailsBottomSheetViewModel
import java.time.Duration


@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsBottomSheet(
    onDismiss: () -> Unit,
    pair: Pair<Event, Group>,
    viewModel: EventDetailsBottomSheetViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState()
    //region Height animation on sliding
    var fullHeight by remember { mutableIntStateOf(0) }
    var heightProgressFraction by remember { mutableFloatStateOf(0f) }

    // TODO: maybe move it in a right way to something like
    //  LaunchedEffect as it says in documentation
    //  Try catch because requireOffset can throw an exception
    //  if call it before first composition
    try {
        val offset = sheetState.requireOffset()
        heightProgressFraction = 1 - (offset / fullHeight)
    } catch (_: Exception) {
    }
    //endregion
    ModalBottomSheet(
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {

        // Sheet content
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            fullHeight = constraints.maxHeight
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(heightProgressFraction)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Header(
                    group = pair.second,
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
                                timeRange = pair.first.initialRange,
                                duration = pair.first.duration,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                            )
                        }
                    }
                }
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
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FromGroupText(group)
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
    modifier: Modifier = Modifier,
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

