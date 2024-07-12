package com.lumen1024.groupeventer.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.auth.ui.AuthScreen
import com.lumen1024.groupeventer.pages.create_event.ui.CreateEventScreen
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.ui.NavigationEffects

@Composable
fun MainNavGraph(

    viewModel: MainNavGraphViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    
    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController
    )
    
    NavHost(
        navController = navController,
        startDestination = Screen.Auth
    ) {
        composable<Screen.Auth> { AuthScreen() }

        composable<Screen.Groups> { GroupsScreen() }
        composable<Screen.Events> { EventsScreen() }
        composable<Screen.Profile> { ProfileScreen() }

        composable<Screen.CreateEvent> { CreateEventScreen() }
    }
}
