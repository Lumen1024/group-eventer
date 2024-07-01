package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen

@Composable
fun HomeScreen(mainNavController: NavController) {
    val homeNavController = rememberNavController()



    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(Color.Cyan),
        bottomBar = {
            HomeNavBar(homeNavController)
        },
    ) { innerPadding ->
        NavHost(
            homeNavController,
            startDestination = Screen.BottomBarScreen.Events.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.BottomBarScreen.Groups.route) { GroupsScreen() }
            composable(Screen.BottomBarScreen.Events.route) { EventsScreen() }
            composable(Screen.BottomBarScreen.Profile.route) { ProfileScreen() }
        }
    }
}