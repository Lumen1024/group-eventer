package com.lumen1024.groupeventer.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.auth.ui.AuthScreen
import com.lumen1024.groupeventer.pages.home.ui.HomeScreen
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.ui.DelegatedNavigation

@Composable
fun MainNavGraph(
    viewModel: MainNavGraphViewModel = hiltViewModel(),
) {
    DelegatedNavigation(
        navigator = viewModel.navigator
    ) {
        composable(Screen.Auth.route) { AuthScreen() }
        composable(Screen.Home.route) { HomeScreen() }
    }
}
