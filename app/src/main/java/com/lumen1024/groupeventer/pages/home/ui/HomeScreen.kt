package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen

@Composable
fun HomeScreen(
    mainNavController: NavHostController
) {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding(),
        bottomBar = {
            HomeNavBar(navController)
        },
    ) { innerPadding ->

        NavHost(
            navController,
            startDestination = Screen.Home.Events.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.Groups.route) { GroupsScreen() }
            composable(Screen.Home.Events.route) { EventsScreen(mainNavController) }
            composable(Screen.Home.Profile.route) { ProfileScreen(mainNavController) }
        }
    }

}