package com.lumen1024.ui.app_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lumen1024.ui.navigation.MainNavGraph

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




