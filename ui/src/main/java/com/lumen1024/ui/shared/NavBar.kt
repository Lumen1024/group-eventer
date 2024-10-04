package com.lumen1024.ui.shared

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
import com.lumen1024.ui.getCurrentScreenAsState
import com.lumen1024.ui.navigation.HasIcon
import com.lumen1024.ui.navigation.HasLabel
import com.lumen1024.ui.navigation.Navigator
import com.lumen1024.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavBarViewModel @Inject constructor(
    val navigator: Navigator,
) : ViewModel()


@Composable
fun NavBar(
    items: List<Screen>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NavBarViewModel = hiltViewModel(),
) {
    val currentScreen by navController.getCurrentScreenAsState()

    NavigationBar(modifier = modifier) {
        items.forEach { screen ->
            NavBarItem(
                screen = screen,
                isSelected = (currentScreen == screen),
                onNavigate = { navigateScreen: Screen ->
                    viewModel.navigator.navigate(
                        navigateScreen,
                    ) {
                        popUpTo(navigateScreen) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.NavBarItem(
    screen: Screen,
    isSelected: Boolean,
    onNavigate: (Screen) -> Unit,
) {

    val label =
        if (screen is HasLabel) stringResource(screen.label) else ""

    val icon by remember {
        derivedStateOf {
            if (screen is HasIcon)
                screen.icon
            else
                Icons.Default.Circle
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
        onClick = { onNavigate(screen) },
    )
}