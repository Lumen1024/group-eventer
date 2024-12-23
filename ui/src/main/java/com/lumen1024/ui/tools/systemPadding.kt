package com.lumen1024.ui.tools

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.systemPadding(): Modifier {
    return this
        .systemBarsPadding()
        .statusBarsPadding()
}