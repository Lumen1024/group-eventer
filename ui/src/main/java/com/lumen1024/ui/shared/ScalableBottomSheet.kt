package com.lumen1024.ui.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ScalableBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable (heightMaxFraction: Float) -> Unit,
) {
    var fullHeight by remember { mutableIntStateOf(0) }
    var heightProgressFraction by remember { mutableFloatStateOf(0f) }

    // TODO: maybe move it in a right way to something like
    //  LaunchedEffect as it says in documentation
    //  Try catch because requireOffset can throw an exception
    //  if call it before first composition
    try {
        val offset = sheetState.requireOffset()
        heightProgressFraction = 1 - (offset / fullHeight)
    } catch (_: Exception) {
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            fullHeight = constraints.maxHeight
            content(heightProgressFraction)
        }
    }
}