package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.shared.config.HasIcon
import com.lumen1024.groupeventer.shared.config.HasLabel
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import com.lumen1024.groupeventer.shared.model.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavBarViewModel @Inject constructor(
    val navigator: Navigator,
) : ViewModel()


@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    items: List<Screen>,
    startDestination: Screen,
    viewModel: NavBarViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val currentScreen by navController.getCurrentScreenAsState()
    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { screen ->
            NavBarItem(
                screen,
                isSelected = currentScreen == screen,
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
    isSelected: Boolean,
    onNavigate: (Screen) -> Unit,
) {

    val label = if (screen is HasLabel) stringResource(screen.label) else ""

    val icon by remember {
        derivedStateOf {
            if (screen is HasIcon) screen.icon else Icons.Default.Circle
        }
    }

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        label = { Text(label) },
        selected = isSelected,
        onClick = { if (!isSelected) onNavigate(screen) },
    )
}