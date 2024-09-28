package com.lumen1024.presentation.shared.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize

@Composable
fun ColorCircle(
    color: Color,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    var sizePx by remember { mutableStateOf(IntSize.Zero) }
    val sizeDp = with(LocalDensity.current) { sizePx.width.toDp() }

    val border by animateDpAsState(
        if (selected) sizeDp else sizeDp / 5,
        label = "Color circle border size"
    )

    Box(
        modifier = modifier
            .onSizeChanged { sizePx = it }
            .border(width = border, color, shape = CircleShape)
            .clip(CircleShape)
    )
}