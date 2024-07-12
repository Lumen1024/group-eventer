package com.lumen1024.groupeventer.shared.ui

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lumen1024.groupeventer.app.navigation.Navigator
import com.lumen1024.groupeventer.shared.config.HasIcon
import com.lumen1024.groupeventer.shared.config.HasLabel
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.config.isEqualsToRoute
import com.lumen1024.groupeventer.shared.lib.LocalNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavBarViewModel @Inject constructor(
    val navigator: Navigator,
) : ViewModel()


@Composable
fun NavBar(
    items: List<Screen>,
    startDestination: Screen,
    viewModel: NavBarViewModel = hiltViewModel(),
) {
    NavigationBar(
        modifier = Modifier
    ) {
        items.forEach { screen ->
            NavBarItem(
                screen,
                onNavigate = { navigateScreen: Screen ->
                    viewModel.navigator.tryNavigateTo(
                        navigateScreen,
                        popUpToRoute = startDestination,
                        isSingleTop = true,
                    )
                })
        }
    }
}

@Composable
private fun RowScope.NavBarItem(
    screen: Screen,
    onNavigate: (Screen) -> Unit,
) {
    val navController = LocalNavController.current
    val currentScreen by navController.currentBackStackEntryAsState()

    val icon = if (screen is HasIcon) screen.icon else Icons.Default.Circle
    val label = if (screen is HasLabel) stringResource(screen.label) else ""

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        label = { Text(label) },
        selected = screen.isEqualsToRoute(currentScreen),
        onClick = { onNavigate(screen) },
    )
}