package com.lumen1024.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.lumen1024.presentation.Screen

@Composable
fun NavHostController.getCurrentScreenAsState(): State<com.lumen1024.presentation.Screen?> {

    val state = this.currentBackStackEntryFlow
        .collectAsState(null)
    return remember {
        derivedStateOf {
            state.value?.destination?.getScreen()
        }
    }
}

fun NavDestination.getScreen(): com.lumen1024.presentation.Screen? {
    val screens = mapOf(
        com.lumen1024.presentation.Screen.Auth.javaClass.canonicalName to com.lumen1024.presentation.Screen.Auth,
        com.lumen1024.presentation.Screen.Tutorial.javaClass.canonicalName to com.lumen1024.presentation.Screen.Tutorial,

        com.lumen1024.presentation.Screen.Groups.javaClass.canonicalName to com.lumen1024.presentation.Screen.Groups,
        com.lumen1024.presentation.Screen.Events.javaClass.canonicalName to com.lumen1024.presentation.Screen.Events,
        com.lumen1024.presentation.Screen.Profile.javaClass.canonicalName to com.lumen1024.presentation.Screen.Profile,

        com.lumen1024.presentation.Screen.CreateEvent.javaClass.canonicalName to com.lumen1024.presentation.Screen.CreateEvent,
    )
    val route = this.route ?: return null
    return screens[route]
}