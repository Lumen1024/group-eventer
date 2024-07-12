package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.lumen1024.groupeventer.shared.model.ScaffoldController

@Composable
fun DelegatedScaffold(
    modifier: Modifier = Modifier,
    scaffoldController: ScaffoldController,
    content: @Composable (PaddingValues) -> Unit
) {
    val topBar by scaffoldController.topBar.collectAsState()
    val bottomBar by scaffoldController.bottomBar.collectAsState()
    val floatingActionButton by scaffoldController.floatingButton.collectAsState()
    Scaffold(
        topBar = topBar ?: {},
        bottomBar = bottomBar ?: {},
        floatingActionButton = floatingActionButton ?: {},
        content = content
    )
}