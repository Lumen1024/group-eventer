package com.lumen1024.groupeventer.shared.ui.time

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import java.time.Instant

@Composable
fun DayMonthButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(0.dp),
    date: Instant,
    onClick: () -> Unit
) {
    val dayStr = TimeRangeFormatter.formatDayMonthWithZone(date).replace(' ', '\n')

    Button(
        modifier = modifier
            .height(48.dp)
            .width(64.dp),
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Text(
            color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
            text = dayStr,
            textAlign = TextAlign.Center,
            style = TextStyle(lineBreak = LineBreak.Simple),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Medium
        )
    }
}