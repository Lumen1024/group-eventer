package com.lumen1024.presentation.shared.ui

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (millis: Long) -> Unit,
    onDismiss: () -> Unit,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    initDisplayMode: DisplayMode = DisplayMode.Picker,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
) {
    val datePickerState = rememberDatePickerState(
        yearRange = yearRange,
        selectableDates = selectableDates,
        initialDisplayMode = initDisplayMode
    )

    val selectedDate = datePickerState.selectedDateMillis ?: 0

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                }
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(android.R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

