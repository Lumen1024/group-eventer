package com.lumen1024.groupeventer.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.navigation.Navigator

@Composable
fun DelegatedNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController = rememberNavController()
    val destination by navigator.destination.collectAsState()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = destination,
        builder = builder
    )
    LaunchedEffect(destination) {
        if (navController.currentDestination?.route != destination) {
            navController.navigate(destination, ) {
                navigator.builder
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                if (navigator.popUpStart)
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
            }
        }
    }
}