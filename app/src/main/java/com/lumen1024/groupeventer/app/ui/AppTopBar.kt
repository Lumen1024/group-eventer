package com.lumen1024.groupeventer.app.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.pages.events.ui.EventsTopBar
import com.lumen1024.groupeventer.pages.profile.ui.ProfileTopBar
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import com.lumen1024.groupeventer.shared.model.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppTopBarViewModel @Inject constructor(
    private val authService: AuthService,
    private val navigator: Navigator
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            authService.logout()
        }
        navigator.navigate(Screen.Auth) {
            popUpTo(0) { inclusive = true }
        }
    }
}

@Composable
fun AppTopBar(
    navController: NavHostController,
    viewModel: AppTopBarViewModel = hiltViewModel(),
) {
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
                Screen.Profile -> ProfileTopBar { viewModel.logout() }
                else -> {}
            }
        }
    }
}