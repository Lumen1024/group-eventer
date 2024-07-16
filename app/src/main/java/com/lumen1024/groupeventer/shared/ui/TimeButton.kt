package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import java.time.Instant

@Composable
fun TimeButton(
    modifier: Modifier = Modifier,
    date: Instant,
    onClick: () -> Unit = {},
    shape: Shape = RoundedCornerShape(8.dp),
) {
    val timeStr = TimeRangeFormatter.formatTimeWithZone(date)

    Button(
        modifier = modifier,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = timeStr,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}