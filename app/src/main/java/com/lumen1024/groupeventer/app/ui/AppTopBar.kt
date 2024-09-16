package com.lumen1024.groupeventer.app.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.pages.events.ui.EventsTopBar
import com.lumen1024.groupeventer.pages.profile.ui.ProfileTopBar
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState

@Composable
fun AppTopBar(navController: NavHostController) {
    val currentScreen by navController.getCurrentScreenAsState()

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AnimatedContent(
            targetState = currentScreen,
            label = "Top bar animation"
        ) { screen ->
            //Text(text = screen.toString()) // TODO
            when (screen) {
                Screen.Auth -> {} // TODO: move top bar here
//                Screen.CreateEvent -> TODO()
                Screen.Events -> EventsTopBar()
                Screen.Groups -> EventsTopBar() // TODO
                Screen.Profile -> ProfileTopBar(navController) { }
                else -> {}
            }
        }
    }
}