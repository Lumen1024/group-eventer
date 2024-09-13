package com.lumen1024.groupeventer.entities.group.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.entities.group.model.GroupColor
import com.lumen1024.groupeventer.shared.ui.ColorCircle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupColorPicker(
    modifier: Modifier = Modifier,
    colors: List<GroupColor>,
    selectedColor: GroupColor,
    onSelect: (GroupColor) -> Unit,
    circleSize: Dp = 24.dp,
) {
    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()

    val scrollTo = { it: Int ->
        coroutineScope.launch {
            scrollState.animateScrollTo(it)
        }
    }

    Row {
        IconButton(
            onClick = { scrollTo(0) }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Color picker left scroll"
            )
        }
        Row(
            modifier = modifier
                .weight(1f)
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            colors.forEach { color ->
                CompositionLocalProvider(
                    LocalRippleConfiguration provides RippleConfiguration(color.color),
                ) {
                    IconButton(
                        onClick = { onSelect(color) }
                    ) {
                        ColorCircle(
                            color = color.color,
                            selected = color == selectedColor,
                            size = circleSize
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { scrollTo(scrollState.maxValue) }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Color picker right scroll"
            )
        }
    }
}