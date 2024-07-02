package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lumen1024.groupeventer.app.navigation.HomeNavigator
import com.lumen1024.groupeventer.shared.config.HaveIcon
import com.lumen1024.groupeventer.shared.config.HaveLabel
import com.lumen1024.groupeventer.shared.config.Screen


@Composable
fun HomeNavBar(navigator: HomeNavigator) {
    val items = listOf(
        Screen.Home.Groups,
        Screen.Home.Events,
        Screen.Home.Profile,
    )
    NavigationBar(
        modifier = Modifier
    ) {
        items.forEach { screen ->
            AddNavigationBarItem(screen, navigator)
        }
    }
}

@Composable
private fun RowScope.AddNavigationBarItem(
    screen: Screen,
    navigator: HomeNavigator,
) {
    val icon = if (screen is HaveIcon) screen.icon else Icons.Default.Circle
    val label = if (screen is HaveLabel) stringResource(screen.label) else ""
    val dest = navigator.destination.collectAsState()

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        label = { Text(label) },
        selected = dest.value == screen.route,
        onClick = {
            navigator.navigate(screen, true) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
//                popUpTo(navController.graph.findStartDestination().id) {
//                    saveState = true
//
//                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
    )
}