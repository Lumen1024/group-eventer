package com.lumen1024.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

@Composable
fun NavHostController.getCurrentScreenAsState(): State<Screen?> {

    val state = this.currentBackStackEntryFlow
        .collectAsState(null)
    return remember {
        derivedStateOf {
            state.value?.destination?.getScreen()
        }
    }
}

fun NavDestination.getScreen(): Screen? {
    val screens = mapOf(
        Screen.Auth.javaClass.canonicalName to Screen.Auth,
        Screen.Tutorial.javaClass.canonicalName to Screen.Tutorial,

        Screen.Groups.javaClass.canonicalName to Screen.Groups,
        Screen.Events.javaClass.canonicalName to Screen.Events,
        Screen.Profile.javaClass.canonicalName to Screen.Profile,

        Screen.CreateEvent.javaClass.canonicalName to Screen.CreateEvent,
    )
    val route = this.route ?: return null
    return screens[route]
}