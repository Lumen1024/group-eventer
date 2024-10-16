package com.lumen1024.ui.shared.time

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.tools.TimeRangeFormatter
import com.lumen1024.domain.tools.throttleLatest
import com.lumen1024.ui.config.GroupEventerTheme
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

private val clipShape = RoundedCornerShape(4.dp)

@Composable
fun TimeRangeSlider(
    initialRange: TimeRange,
    duration: Duration,
    onChange: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
    step: Duration = Duration.ofMinutes(1),
) {
    val all: Int = (initialRange.duration.toMillis() / step.toMillis()).toInt()
    val selected: Int = (duration.toMillis() / step.toMillis()).toInt()

    val ratio = selected.toFloat() / all.toFloat()
    var offset by remember { mutableIntStateOf(0) }

    var containerWidth by remember { mutableIntStateOf(0) }
    var indicatorWidth by remember { mutableIntStateOf(0) }

    val stepPx by remember { derivedStateOf { containerWidth / all } }
    var offsetPx by remember { mutableIntStateOf(0) }
    val animatedOffset by animateIntAsState(
        targetValue = offsetPx,
        label = "offset animation"
    )
    var indicatorScale by remember { mutableFloatStateOf(1f) }
    val animatedIndicatorScale by animateFloatAsState(
        targetValue = indicatorScale,
        label = "indicator scale animation"
    )

    val startTime by remember {
        derivedStateOf {
            initialRange.start + Duration.ofMillis(step.toMillis() * offset)
        }
    }
    val startTimeText by remember {
        derivedStateOf { TimeRangeFormatter.formatTimeWithZone(startTime) }
    }

    val onDrag: (Float) -> Unit = onDrag@{ delta ->
        indicatorScale = 1.1f
        if (delta + offsetPx + indicatorWidth > containerWidth) return@onDrag
        if (delta + offsetPx < 0) return@onDrag

        offsetPx += delta.toInt()

    }
    val onDragStopped: () -> Unit = {
        offsetPx.roundToNearestMultiple(stepPx)
        indicatorScale = 1f
        onChange(
            TimeRange(
                startTime,
                startTime.plusMillis(duration.toMillis())
            )
        )
    }

    Box(
        modifier
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest, clipShape)
            .offset { IntOffset(animatedOffset, 0) }
            .onGloballyPositioned { layoutCoordinates ->
                containerWidth = layoutCoordinates.size.width
            }
    ) {
        TimeRangeSliderIndicator(
            modifier = Modifier
                .fillMaxHeight()
                .graphicsLayer {
                    scaleX = animatedIndicatorScale
                    scaleY = animatedIndicatorScale
                }
                .onGloballyPositioned { layoutCoordinates ->
                    indicatorWidth = layoutCoordinates.size.width
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState(onDrag),
                    onDragStopped = { onDragStopped() }
                ),
            ratio = ratio,
            timeText = startTimeText
        )
    }
}

// TODO: refactor
@Composable
fun TimeRangeSlider1(
    modifier: Modifier,
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

    val coroutineScope = rememberCoroutineScope()
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
                        onChange(
                            TimeRange(
                                startTime,
                                startTime.plusMillis(duration.toMillis())
                            )
                        )
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
    val timeRange by remember {
        mutableStateOf(
            TimeRange(
                Instant.now().truncatedTo(ChronoUnit.HOURS),
                Instant.now().truncatedTo(ChronoUnit.HOURS) + Duration.ofHours(4)
            )
        )
    }
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
                initialRange = timeRange,
                duration = duration,
                onChange = { newTimeRange = it },
                step = Duration.ofMinutes(15)
            )
        }
    }
}

/**
 * Rounds this integer to the nearest multiple of the given [multiple].
 *
 * If this integer is exactly halfway between two multiples, it is rounded up.
 *
 * @param multiple The multiple to round to.
 * @return The rounded integer.
 * @throws IllegalArgumentException if [multiple] is 0.
 */
fun Int.roundToNearestMultiple(multiple: Int): Int {
    val remainder = this % multiple
    return if (remainder < multiple / 2) {
        this - remainder
    } else {
        this + (multiple - remainder)
    }
}