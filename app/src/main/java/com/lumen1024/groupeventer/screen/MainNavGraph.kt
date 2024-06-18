package com.lumen1024.groupeventer.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.screen.auth.AuthScreen
import com.lumen1024.groupeventer.screen.auth.AuthViewModel
import com.lumen1024.groupeventer.screen.home.HomeScreen
import com.lumen1024.groupeventer.screen.splash.SplashScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }


        composable(Screen.Auth.route) {
            val vm : AuthViewModel = hiltViewModel()
            vm.navController = navController
            AuthScreen(hiltViewModel())
        }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}