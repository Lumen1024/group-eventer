package com.lumen1024.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lumen1024.domain.usecase.AuthService
import com.lumen1024.ui.screen.auth.AuthScreen
import com.lumen1024.ui.screen.create_event.CreateEventScreen
import com.lumen1024.ui.screen.events.EventsScreen
import com.lumen1024.ui.screen.groups.GroupsScreen
import com.lumen1024.ui.screen.profile.ProfileScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavGraphViewModel @Inject constructor(
    navigator: Navigator,
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
            enterTransition = { getRelativeSlideInTransition(right = listOf(
                Screen.Events,
                Screen.Profile
            )) },
            exitTransition = { getRelativeSlideOutTransition(right = listOf(
                Screen.Events,
                Screen.Profile
            )) },
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
            enterTransition = { getRelativeSlideInTransition(left = listOf(
                Screen.Events,
                Screen.Groups
            )) },
            exitTransition = { getRelativeSlideOutTransition(left = listOf(
                Screen.Events,
                Screen.Groups
            )) },
        ) { ProfileScreen() }

        composable<Screen.CreateEvent> { CreateEventScreen() }
    }
}

