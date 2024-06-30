package com.lumen1024.groupeventer.shared.ui.time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import com.lumen1024.groupeventer.shared.model.TimeRange


@Composable
fun TimeRangePicker(value: TimeRange, onChange: (TimeRange) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimeRangeButton(
            date = value.start,
            onChanged = { onChange(value.copy(start = it)) }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
        TimeRangeButton(
            mirrored = true,
            date = value.end,
            onChanged = { onChange(value.copy(end = it)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeRangePickerPreview() {
    var timeRange by remember { mutableStateOf(TimeRange()) }

    GroupEventerTheme {
        Box(Modifier.padding(16.dp)) {
            TimeRangePicker(value = timeRange, onChange = { timeRange = it })
        }
    }
}