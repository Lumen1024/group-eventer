package com.lumen1024.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun AppContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { AppTopBar(navController = navController) },
        bottomBar = { AppBottomBar(navController = navController) },
        floatingActionButton = { AppFloatingButton(navController = navController) },
    ) {
        com.lumen1024.presentation.MainNavGraph(
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}




