package com.lumen1024.ui.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
inline fun ScalableBottomSheet(
    noinline onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    crossinline content: @Composable BoxWithConstraintsScope.(
        heightMaxFraction: Float,
        partiallyExpandedHeight: Dp
    ) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val offset by remember {
                derivedStateOf {
                    try {
                        sheetState.requireOffset()
                    } catch (_: Exception) {
                        0f
                    }
                }
            }
            val heightProgressFraction by remember {
                derivedStateOf { 1 - (offset / constraints.maxHeight) }
            }
            val density = LocalDensity.current
            val partiallyExpandedHeight by remember {
                derivedStateOf {
                    with(density) {
                        (constraints.maxHeight * 0.4645f).toDp()
                    }
                }
            }
            content(heightProgressFraction, partiallyExpandedHeight)
        }
    }
}