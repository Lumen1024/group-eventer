package com.lumen1024.groupeventer.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.data.events.GroupEvent
import com.lumen1024.groupeventer.screen.Screen
import com.lumen1024.groupeventer.screen.home.events.EventsScreen

@Composable
fun HomeScreen(upNavController: NavController) {
    val navController = rememberNavController()
    val fabVisibility = remember { mutableStateOf(true) }
    val dialogVisibility = remember { mutableStateOf(false) }
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
                    dialogVisibility.value = true
                }) {
                    Icon(Icons.Default.Add, "")
                }
        }


    ) { innerPadding ->
        if (dialogVisibility.value)
        {
            AddDialog(onDismissRequest = {dialogVisibility.value = false})
        }
        NavHost(
            navController,
            startDestination = Screen.EventList.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.EventList.route) { EventsScreen() }
            composable(Screen.MyTime.route) { ProfileScreen() }
            composable(Screen.Settings.route) { SettingsScreen(navController = upNavController) }
        }
    }
}

@Composable
fun AddDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    icon: ImageVector = Icons.Default.Add,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "ded")
        },
        text = {
            Text(text = "dd")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val db = Firebase.firestore
                    db.collection("events").add(
                        GroupEvent(
                        name = "ded",
                        description = "lolik",
                    )
                    )
                    onConfirmation()
                }
            ) {
                Text("Confirm")

            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(modifier: Modifier = Modifier) {
    //TopAppBar(title = { Text(text = "ded") })
}

@Composable
fun MainBottomBar(navController: NavHostController, fabVisibility: MutableState<Boolean>) {
    val items = listOf(
        Screen.EventList,
        Screen.MyTime,
        Screen.Settings,
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        is Screen.EventList -> Icon(
                            Icons.Default.Home,
                            ""
                        )

                        is Screen.MyTime -> Icon(
                            Icons.Filled.Face,
                            ""
                        )

                        is Screen.Settings -> Icon(
                            Icons.Filled.Settings,
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