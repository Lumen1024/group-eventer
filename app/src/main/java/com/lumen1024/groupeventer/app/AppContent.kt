package com.lumen1024.groupeventer.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.app.scaffold_components.AppBottomBar
import com.lumen1024.groupeventer.app.scaffold_components.AppFloatingButton
import com.lumen1024.groupeventer.app.scaffold_components.AppTopBar
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val currentScreen by navController.getCurrentScreenAsState()

    Scaffold(
        //modifier = Modifier.systemPadding(),
        topBar = { AppTopBar(navController = navController)},
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




