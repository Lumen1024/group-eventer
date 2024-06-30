package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.pages.events.ui.EventsScreen
import com.lumen1024.groupeventer.pages.groups.ui.GroupsScreen
import com.lumen1024.groupeventer.pages.profile.ui.ProfileScreen
import com.lumen1024.groupeventer.shared.config.Screen

@Composable
fun HomeScreen(upNavController: NavController) {
    val navController = rememberNavController()
    //val currentScreenRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(Color.Cyan),
        topBar = {

        },
        bottomBar = {
            MainBottomBar(navController)
        },


    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.BottomBarScreen.Events.route,
            Modifier.padding(innerPadding)
        ) {

            composable(Screen.BottomBarScreen.Groups.route) { GroupsScreen() }
            composable(Screen.BottomBarScreen.Events.route) { EventsScreen() }
            composable(Screen.BottomBarScreen.Profile.route) { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = "Типо тайтл", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun MainBottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.BottomBarScreen.Groups,
        Screen.BottomBarScreen.Events,
        Screen.BottomBarScreen.Profile,
    )
    NavigationBar(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 60.dp)
            .clip(RoundedCornerShape(100.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            AddNavigationBarItem(screen, currentDestination, navController)
        }
    }
}

@Composable
private fun RowScope.AddNavigationBarItem(
    screen: Screen.BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(screen.title)
            )
        },
        label = { Text(stringResource(screen.title)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true

                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
    )
}