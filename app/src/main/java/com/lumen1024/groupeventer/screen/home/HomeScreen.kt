package com.lumen1024.groupeventer.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.MainViewModel
import com.lumen1024.groupeventer.data.GroupEvent
import com.lumen1024.groupeventer.events.EventsScreen
import com.lumen1024.groupeventer.screen.Screen

@Composable
fun HomeScreen(viewmodel: MainViewModel = viewModel()) {
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
            composable(Screen.Settings.route) { SettingsScreen() }
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
                    db.collection("events").add(GroupEvent(
                        name = "ded",
                        description = "lolik",
                    ))
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

@Composable
fun MainTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = { Text(text = "ded") })
}

@Composable
fun MainBottomBar(navController: NavHostController, fabVisibility: MutableState<Boolean>) {
    val items = listOf(
        Screen.EventList,
        Screen.MyTime,
        Screen.Settings,
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        is Screen.EventList -> Icon(
                            Icons.AutoMirrored.Filled.List,
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