package com.lumen1024.presentation.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import java.time.Instant

@Composable
fun DayMonthButton(
    date: Instant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    colors: ButtonColors =
        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
) {
    val day = TimeRangeFormatter
        .formatDayMonthWithZone(date)
        .replace(' ', '\n')

    Button(
        modifier = modifier,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        colors = colors,
        onClick = onClick
    ) {
        Text(
            color = MaterialTheme.colorScheme.contentColorFor(colors.containerColor),
            text = day,
            textAlign = TextAlign.Center,
            style = TextStyle(lineBreak = LineBreak.Simple),
        )
    }
}