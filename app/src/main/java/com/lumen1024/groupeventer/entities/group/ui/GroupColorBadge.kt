package com.lumen1024.groupeventer.entities.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupColorBadge(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .size(20.dp)
            .background(color)
            .then(modifier)
    )
}