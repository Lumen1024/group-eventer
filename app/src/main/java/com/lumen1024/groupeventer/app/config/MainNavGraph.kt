package com.lumen1024.groupeventer.app.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.pages.auth.ui.AuthScreen
import com.lumen1024.groupeventer.pages.create_event.ui.CreateEventScreen
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getRelativeSlideInTransition
import com.lumen1024.groupeventer.shared.lib.getRelativeSlideOutTransition
import com.lumen1024.groupeventer.shared.model.Navigator
import com.lumen1024.groupeventer.shared.ui.NavigationEffects
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavGraphViewModel @Inject constructor(
    private val navigator: Navigator,
    private val authService: AuthService,
) : ViewModel() {

    val navigationChannel = navigator.navigationChannel

    fun getStartDestination(): Screen = if (authService.checkAuthorized())
        Screen.Events
    else
        Screen.Auth
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
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
        composable<Screen.Auth> { AuthScreen() }

        composable<Screen.Groups>(
            enterTransition = { getRelativeSlideInTransition(right = listOf(Screen.Events, Screen.Profile)) },
            exitTransition = { getRelativeSlideOutTransition(right = listOf(Screen.Events, Screen.Profile)) },
        ) { GroupsScreen() }

        composable<Screen.Events>(
            enterTransition = {
                getRelativeSlideInTransition(
                    left = listOf(Screen.Groups),
                    right = listOf(Screen.Profile)
                )
            },
            exitTransition = {
                getRelativeSlideOutTransition(
                    left = listOf(Screen.Groups),
                    right = listOf(Screen.Profile)
                )
            },
        ) { EventsScreen() }

        composable<Screen.Profile>(
            enterTransition = { getRelativeSlideInTransition(left = listOf(Screen.Events, Screen.Groups)) },
            exitTransition = { getRelativeSlideOutTransition(left = listOf(Screen.Events, Screen.Groups)) },
        ) { ProfileScreen() }

        composable<Screen.CreateEvent> { CreateEventScreen() }
    }
}

