package com.lumen1024.groupeventer.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.auth.ui.AuthScreen
import com.lumen1024.groupeventer.pages.create_event.ui.CreateEventScreen
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.lib.LocalNavController
import com.lumen1024.groupeventer.shared.model.ScaffoldController

import com.lumen1024.groupeventer.shared.ui.NavigationEffects


@Composable
fun MainNavGraph(
    navController: NavHostController,
    scaffoldController: ScaffoldController,
    modifier: Modifier = Modifier,
    viewModel: MainNavGraphViewModel = hiltViewModel(),
) {

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController
    )

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = viewModel.getStartDestination()
    ) {
        composable<Screen.Auth> { AuthScreen(scaffoldController) }

        composable<Screen.Groups> { GroupsScreen(scaffoldController) }
        composable<Screen.Events> { EventsScreen(scaffoldController) }
        composable<Screen.Profile> { ProfileScreen(scaffoldController) }

        composable<Screen.CreateEvent> { CreateEventScreen(scaffoldController) }
    }
}
