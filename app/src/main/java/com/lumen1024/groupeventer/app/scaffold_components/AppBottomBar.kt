package com.lumen1024.groupeventer.app.scaffold_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.app.config.bottomBarItems
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import com.lumen1024.groupeventer.shared.ui.NavBar

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val showBottomBar by remember { derivedStateOf { currentScreen in bottomBarItems } }
    if (showBottomBar) {
        NavBar(
            items = bottomBarItems,
            startDestination = Screen.Events,
            navController = navController,
        )
    }
}