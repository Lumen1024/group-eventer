package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    val fabVisibility = remember { mutableStateOf(true) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopBar()
        },
        bottomBar = {
            MainBottomBar(navController, fabVisibility)
        },
        floatingActionButton = {
            if (fabVisibility.value)
                FloatingActionButton(onClick = {
                    // todo
                }) {
                    Icon(Icons.Default.Add, "")
                }
        }


    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Events.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Groups.route) { GroupsScreen() }
            composable(Screen.Events.route) { EventsScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = { Text(text = "Типо тайтл", color = Color.White) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))
}

@Composable
fun MainBottomBar(navController: NavHostController, fabVisibility: MutableState<Boolean>) {
    val items = listOf(
        Screen.Groups,
        Screen.Events,
        Screen.Profile,
    )
    NavigationBar(modifier = Modifier.padding(15.dp).clip(RoundedCornerShape(100.dp))) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        is Screen.Groups -> Icon(
                            Icons.Default.Groups,
                            ""
                        )

                        is Screen.Events -> Icon(
                            Icons.AutoMirrored.Filled.List,
                            ""
                        )

                        is Screen.Profile -> Icon(
                            Icons.Filled.Person,
                            ""
                        )

                        else -> {}
                    }

                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {

                    fabVisibility.value = (screen.route == "events")
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
                }
            )
        }
    }
}