package com.lumen1024.groupeventer.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.screen.home.HomeScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Auth.route) { AuthScreen() }
        composable(Screen.Home.route) { HomeScreen() }
    }
}