package com.lumen1024.groupeventer.shared.ui

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import java.time.Instant


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangePicker(date: Instant) {
    val rangeState = RangeSliderState(
        activeRangeStart = 0f,
        activeRangeEnd = 24f,
        steps = 24
    )
    var sliderValues by remember { mutableStateOf(2f..22f) }

    val startInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    val isDatePickerOpen = remember {
        mutableStateOf(false)
    }

    val currentDate = remember {
        mutableStateOf(date)
    }

    val startTime = Instant.ofEpochSecond((sliderValues.start * 60 * 60).toLong())
    val endTime = Instant.ofEpochSecond((sliderValues.endInclusive * 60 * 60).toLong())

    val timePickerCurrentTimeIndex = remember {
        mutableIntStateOf(0)
    }

    Card {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            if (isDatePickerOpen.value) {
                DatePickerDialog(
                    onDateSelected = { currentDate.value = Instant.ofEpochMilli(it) },
                    onDismiss = { isDatePickerOpen.value = false })
            }
            if (timePickerCurrentTimeIndex.intValue != 0) {
                TimePickerDialog(
                    initialHour = if (timePickerCurrentTimeIndex.intValue == 1) sliderValues.start.toInt() else sliderValues.endInclusive.toInt(),
                    initialMinute = 0,
                    onCancel = {
                        timePickerCurrentTimeIndex.intValue = 0
                    },
                    onConfirm = { calendar ->
                        val hours =
                            calendar.get(Calendar.HOUR_OF_DAY).toFloat()
                        val minutes = calendar.get(Calendar.MINUTE)
                            .toFloat() / 60

                        val time = hours + minutes

                        Log.d("w", minutes.toString())

                        when (timePickerCurrentTimeIndex.intValue) {
                            1 -> sliderValues = time..sliderValues.endInclusive

                            2 -> sliderValues = sliderValues.start..time
                        }

                        timePickerCurrentTimeIndex.intValue = 0
                    })
            }
            DayMonthBadge(
                modifier = Modifier.clickable { isDatePickerOpen.value = true },
                date = currentDate.value
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeRangeButton(
                            modifier = Modifier.weight(1f),
                            time = startTime,
                            onClick = { timePickerCurrentTimeIndex.intValue = 1 })
                        Text(text = "—")
                        TimeRangeButton(
                            modifier = Modifier.weight(1f),
                            time = endTime,
                            onClick = { timePickerCurrentTimeIndex.intValue = 2 })
                    }
                }
                RangeSlider(
                    value = sliderValues,
                    valueRange = 0f..24f,
                    steps = 23,
                    onValueChange = { sliderValues = it },
                    colors = colors(inactiveTrackColor = MaterialTheme.colorScheme.secondary)

//            state = rangeState,
//            startThumb = {
//                TimeRangeThumb(
//                    value = rangeState.value.activeRangeStart,
//                    interactionSource = startInteractionSource
//                )
//            },
//            endThumb = {
//                TimeRangeThumb(
//                    value = rangeState.value.activeRangeEnd,
//                    interactionSource = endInteractionSource
//                )
//            }
                )
            }
        }
    }
}

@Composable
fun DayMonthBadge(modifier: Modifier = Modifier, date: Instant) {
    val day = TimeRangeFormatter.formatDayMonth(date)

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
            text = day.replace(' ', '\n'),
            textAlign = TextAlign.Center,
            style = TextStyle(lineBreak = LineBreak.Simple)
        )
    }
}

@Composable
fun TimeRangeButton(modifier: Modifier = Modifier, time: Instant, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = TimeRangeFormatter.formatTime(time),
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeThumb(
    value: Float,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    colors: SliderColors = colors(),
    enabled: Boolean = true
) {
    Column {
        SliderDefaults.Thumb(interactionSource, modifier, colors, enabled)
        Text(text = value.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun TimeRangePickerPreview() {
    val date = Instant.now()

    GroupEventerTheme {
        Box(Modifier.padding(16.dp)) {
            TimeRangePicker(date)
        }
    }
}