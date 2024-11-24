package com.lumen1024.ui.shared.tab

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TabSwitch(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    rowCornerRadius: Dp = 20.dp,
    tabCornerRadius: Dp = 20.dp,
    tabHeight: Dp = 40.dp,
    tabs: @Composable () -> Unit,
) {
    var tabIndexes by remember {
        mutableStateOf(emptyList<TabPosition>())
    }

    val tabBackgroundLeft by animateFloatAsState(
        tabIndexes.getOrNull(selectedTabIndex)?.left?.value ?: 0f,
        label = "Tab background"
    )

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .clip(RoundedCornerShape(rowCornerRadius))
            .drawBehind {
                drawRoundRect(unselectedColor)

                val tabPosition = tabIndexes[selectedTabIndex]

                drawRoundRect(
                    color = selectedColor,
                    cornerRadius = CornerRadius(tabCornerRadius.toPx()),
                    topLeft = Offset(tabBackgroundLeft.dp.toPx(), 0.dp.toPx()),
                    size = Size(tabPosition.width.toPx(), tabHeight.toPx())
                )
            },
        containerColor = Color.Transparent,
        indicator = {
            tabIndexes = it
            Box { }
        },
        divider = {},
        tabs = tabs
    )
}