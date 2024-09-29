package com.lumen1024.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lumen1024.presentation.screen.MainNavGraph

@Composable
fun AppContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { AppTopBar(navController = navController) },
        bottomBar = { AppBottomBar(navController = navController) },
        floatingActionButton = { AppFloatingButton(navController = navController) },
    ) {
        MainNavGraph(
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}




