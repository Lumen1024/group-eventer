package com.lumen1024.ui.shared

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme


@Composable
fun TimeRangePicker(
    value: TimeRange,
    onChange: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimeRangeButton(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            date = value.start,
            onChanged = { onChange(value.copy(start = it)) }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        TimeRangeButton(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            mirrored = true,
            date = value.end,
            onChanged = { onChange(value.copy(end = it)) }
        )
    }
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.NONE,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TimeRangePickerPreview() {
    var timeRange by remember { mutableStateOf(TimeRange()) }

    GroupEventerTheme {
        Box(Modifier.padding(16.dp)) {
            TimeRangePicker(value = timeRange, onChange = { timeRange = it })
        }
    }
}