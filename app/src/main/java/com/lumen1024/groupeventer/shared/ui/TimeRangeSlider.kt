package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import com.lumen1024.groupeventer.shared.lib.throttleLatest
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import com.lumen1024.groupeventer.shared.model.TimeRange
import java.time.Duration
import kotlin.math.roundToInt

private val clipShape = RoundedCornerShape(4.dp)

// TODO: refactor
@Composable
fun TimeRangeSlider(
    modifier: Modifier = Modifier,
    timeRange: TimeRange,
    duration: Duration,
    onChange: (timeRange: TimeRange) -> Unit,
    step: Duration = Duration.ofMinutes(15),
) {
    val ratio by remember {
        derivedStateOf {
            duration.toMillis().toFloat() / timeRange.duration.toMillis().toFloat()
        }
    }

    var startTime by remember { mutableStateOf(timeRange.start) }

    val timeText by remember { derivedStateOf { TimeRangeFormatter.formatTimeWithZone(startTime) } }

    var containerWidth by remember { mutableIntStateOf(0) }

    var indicatorWidth by remember { mutableIntStateOf(0) }
    var indicatorOffsetX by remember { mutableFloatStateOf(0f) }
    var finalIndicatorOffsetX by remember { mutableFloatStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()

    val stepPx by remember {
        derivedStateOf {
            (step.toMillis().toFloat() / timeRange.duration.toMillis()
                .toFloat() * containerWidth).toLong()
        }
    }

    val updateStartTime: (Float) -> Unit = { offsetX ->
        val start = timeRange.start.plusMillis(
            (offsetX / containerWidth * timeRange.duration.toMillis()).toLong()
        )

        startTime = start
    }

    val updateStartTimeThrottle: (Float) -> Unit =
        throttleLatest(
            intervalMs = 32,
            coroutineScope = coroutineScope,
            destinationFunction = updateStartTime
        )


    val onIndicatorDrag: (Float) -> Unit = onIndicatorDrag@{ delta ->
        val newOffset = indicatorOffsetX + delta

        val leftBound = 0
        val rightBound = containerWidth - indicatorWidth

        if (newOffset < leftBound || newOffset > rightBound) {
            return@onIndicatorDrag
        }


        indicatorOffsetX = newOffset

        val fraction = newOffset % stepPx
        finalIndicatorOffsetX = newOffset - fraction
        updateStartTimeThrottle(finalIndicatorOffsetX)
    }

    Box(
        modifier
            .clip(clipShape)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .offset { IntOffset(finalIndicatorOffsetX.roundToInt(), 0) }
            .onGloballyPositioned { layoutCoordinates ->
                containerWidth = layoutCoordinates.size.width
            }
    ) {
        TimeRangeSliderIndicator(
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    indicatorWidth = layoutCoordinates.size.width
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState(onIndicatorDrag),
                    onDragStopped = {
                        onChange(TimeRange(startTime, startTime.plusMillis(duration.toMillis())))
                    }
                ),
            ratio = ratio,
            timeText = timeText
        )
    }
}

@Composable
private fun TimeRangeSliderIndicator(
    ratio: Float,
    timeText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .fillMaxHeight()
            .fillMaxWidth(ratio)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onTertiary,
            text = timeText
        )
    }
}

@Preview
@Composable
private fun TimeRangeSliderPreview() {
    val timeRange by remember { mutableStateOf(TimeRange()) }
    val duration by remember { mutableStateOf(Duration.ofHours(1).plusMinutes(30)) }

    var newTimeRange by remember { mutableStateOf(TimeRange()) }

    GroupEventerTheme {
        Row(
            modifier = Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {

            TimeRangeSlider(
                modifier = Modifier
                    .padding(16.dp)
                    .width(325.dp)
                    .height(32.dp),
                timeRange = timeRange,
                duration = duration,
                onChange = { newTimeRange = it },
            )
        }
    }
}