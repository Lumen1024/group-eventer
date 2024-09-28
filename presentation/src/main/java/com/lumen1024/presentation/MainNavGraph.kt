package com.lumen1024.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lumen1024.groupeventer.shared.lib.getRelativeSlideInTransition
import com.lumen1024.groupeventer.shared.lib.getRelativeSlideOutTransition
import com.lumen1024.groupeventer.shared.model.Navigator
import com.lumen1024.groupeventer.shared.ui.NavigationEffects
import com.lumen1024.presentation.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavGraphViewModel @Inject constructor(
    navigator: Navigator,
    private val authService: com.lumen1024.domain.AuthService,
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
        composable<Screen.Auth> { com.lumen1024.presentation.screen.auth.ui.AuthScreen() }

        composable<Screen.Groups>(
            enterTransition = { getRelativeSlideInTransition(right = listOf(Screen.Events, Screen.Profile)) },
            exitTransition = { getRelativeSlideOutTransition(right = listOf(Screen.Events, Screen.Profile)) },
        ) { com.lumen1024.presentation.screen.groups.ui.GroupsScreen() }

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
        ) { com.lumen1024.presentation.screen.events.ui.EventsScreen() }

        composable<Screen.Profile>(
            enterTransition = { getRelativeSlideInTransition(left = listOf(Screen.Events, Screen.Groups)) },
            exitTransition = { getRelativeSlideOutTransition(left = listOf(Screen.Events, Screen.Groups)) },
        ) { com.lumen1024.presentation.screen.profile.ui.ProfileScreen() }

        composable<Screen.CreateEvent> { com.lumen1024.presentation.screen.create_event.ui.CreateEventScreen() }
    }
}

