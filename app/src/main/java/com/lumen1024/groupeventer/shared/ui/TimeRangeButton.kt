package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.model.FutureDates
import com.lumen1024.groupeventer.shared.model.RoundedLeftShape
import com.lumen1024.groupeventer.shared.model.RoundedRightShape
import java.time.Instant
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeButton(
    date: Instant,
    onChanged: (date: Instant) -> Unit,
    mirrored: Boolean = false
) {
    var isDateDialogOpen by remember {
        mutableStateOf(false)
    }

    var isTimePickerDialogOpen by remember {
        mutableStateOf(false)
    }

    val timeButton = @Composable {
        TimeButton(
            modifier = Modifier
                .width(96.dp),
            shape = if (mirrored) RoundedLeftShape else RoundedRightShape,
            date = date,
            onClick = { isTimePickerDialogOpen = true }
        )
    }

    val dayMonthButton = @Composable {
        DayMonthButton(
            shape = if (mirrored) RoundedRightShape else RoundedLeftShape,
            date = date,
            onClick = { isDateDialogOpen = true },
        )
    }

    Row(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        if (mirrored) {
            timeButton()
            dayMonthButton()
        } else {
            dayMonthButton()
            timeButton()
        }

        if (isTimePickerDialogOpen) {
            TimePickerDialog(
                initialHour = date.atZone(ZoneId.systemDefault()).hour,
                initialMinute = date.atZone(ZoneId.systemDefault()).minute,
                onCancel = { isTimePickerDialogOpen = false },
                onConfirm = { onChanged(it.time.toInstant()) }
            )
        }

        if (isDateDialogOpen) {
            DatePickerDialog(
                selectableDates = FutureDates,
                onDateSelected = { onChanged(Instant.ofEpochMilli(it)) },
                onDismiss = { isDateDialogOpen = false })
        }
    }
}