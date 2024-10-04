package com.lumen1024.ui.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.tools.TimeRangeFormatter
import java.time.Instant

@Composable
fun TimeButton(
    date: Instant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
) {
    val time = TimeRangeFormatter.formatTimeWithZone(date)

    Button(
        modifier = modifier,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}