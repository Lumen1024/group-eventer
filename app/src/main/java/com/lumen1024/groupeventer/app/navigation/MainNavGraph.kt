package com.lumen1024.groupeventer.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.auth.model.AuthViewModel
import com.lumen1024.groupeventer.pages.auth.ui.AuthScreen
import com.lumen1024.groupeventer.pages.home.ui.HomeScreen
import com.lumen1024.groupeventer.shared.config.Screen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Auth.route) {
            val vm: AuthViewModel = hiltViewModel()
            vm.navController = navController
            AuthScreen(hiltViewModel())
        }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}