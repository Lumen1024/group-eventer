package com.lumen1024.ui.tools

import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.dashedHorizontalDivider(
    count: Int,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    @FloatRange(from = 0.0, to = 1.0) heightWeight: Float = 0.8f
): Modifier {
    val thicknessPx = with(LocalDensity.current) { thickness.toPx() }
    return drawBehind {
        val step = size.width / count
        val lineWidth = thicknessPx
        val lineHeight = size.height * heightWeight

        for (i in 1 until count)
            drawRoundRect(
                color = color,
                topLeft = Offset(step * i - lineWidth / 2, (size.height - lineHeight) / 2),
                size = size.copy(width = lineWidth, height = lineHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
    }
}