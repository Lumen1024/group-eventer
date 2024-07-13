package com.lumen1024.groupeventer.shared.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColorCircle(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean = false,
    size: Dp = 24.dp,
) {
    val border by animateDpAsState(
        if (selected) size else size / 5,
        label = "Color circle border size"
    )

    Box(
        modifier = modifier
            .border(width = border, color, shape = CircleShape)
            .size(size)
            .clip(CircleShape)
    )
}