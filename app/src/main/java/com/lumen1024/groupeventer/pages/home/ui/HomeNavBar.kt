package com.lumen1024.groupeventer.pages.home.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lumen1024.groupeventer.shared.config.Screen


@Composable
fun HomeNavBar(navController: NavHostController) {
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