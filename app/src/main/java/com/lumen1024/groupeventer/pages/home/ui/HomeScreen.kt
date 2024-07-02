package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.ui.DelegatedNavigation

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            HomeNavBar(navigator = viewModel.navigator)
        },
    ) { innerPadding ->

        DelegatedNavigation(
            navigator = viewModel.navigator,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.Groups.route) { GroupsScreen() }
            composable(Screen.Home.Events.route) { EventsScreen() }
            composable(Screen.Home.Profile.route) { ProfileScreen() }
        }
    }

}