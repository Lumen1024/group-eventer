package com.lumen1024.groupeventer.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.navigation.Navigator
import com.lumen1024.groupeventer.shared.config.Screen

@Composable
fun DelegatedNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController = rememberNavController()
    val destination by navigator.destination.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navController,
        route = route,
        startDestination = navigator.startDestination.route,
        builder = builder
    )

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute === null) {
            return@LaunchedEffect
        }

        if (destination === null) {
            return@LaunchedEffect
        }

        if (destination === currentRoute) {
            return@LaunchedEffect
        }

        val foundScreen = Screen.fromStr(currentRoute)
        if (foundScreen === null) {
            return@LaunchedEffect
        }

        navigator.setDestination(foundScreen)
    }

    LaunchedEffect(destination) {
        if (currentRoute === null || destination === null || currentRoute === destination) {
            return@LaunchedEffect
        }

        navController.navigate(destination!!) {
            navigator.builder
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            if (navigator.popUpStart)
                navController.graph.findStartDestination().route?.let {
                    popUpTo(it) {
                        saveState = true
                    }
                }

        }
    }


}