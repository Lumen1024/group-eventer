package com.lumen1024.groupeventer.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.config.bottomBarItems
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.LocalNavController
import com.lumen1024.groupeventer.shared.model.ScaffoldController
import com.lumen1024.groupeventer.shared.ui.DelegatedScaffold
import com.lumen1024.groupeventer.shared.ui.NavBar

@Composable
fun ApplicationContentOld() {
    val navController = rememberNavController()
    val scaffoldController = ScaffoldController()

    CompositionLocalProvider(LocalNavController provides navController) {
        DelegatedScaffold(
            scaffoldController,
            modifier = Modifier
                .systemBarsPadding()
                .statusBarsPadding()
        ) {
            MainNavGraph(
                navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()



    Scaffold(
        modifier = Modifier.systemPadding(),
        bottomBar = { AppBottomBar(navController = navController) }
    ) {
        MainNavGraph(
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {

}

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentScreen = Screen.Events
    val showBottomBar by remember { derivedStateOf { currentScreen in bottomBarItems } }
    if (showBottomBar) {
        NavBar(
            items = bottomBarItems,
            startDestination = Screen.Events,
            navController = navController,
        )
    }
}

@Composable
fun AppFloatingButton(modifier: Modifier = Modifier) {

}

@Composable
fun Modifier.systemPadding(): Modifier {
    return this
        .systemBarsPadding()
        .statusBarsPadding()
}