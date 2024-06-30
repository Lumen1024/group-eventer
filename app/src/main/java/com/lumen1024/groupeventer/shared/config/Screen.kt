package com.lumen1024.groupeventer.shared.config

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.lumen1024.groupeventer.R

sealed class Screen(open val route: String) {
    data object Auth : Screen("auth")
    data object Tutorial : Screen("tutorial")
    data object Home : Screen("home")

    
    sealed class BottomBarScreen(
        override val route: String,
        @StringRes val title: Int,
        val icon: ImageVector,
    ) : Screen(route) {
        data object Groups :
            BottomBarScreen("profile", R.string.groups_screen, Icons.Default.Groups)

        data object Events :
            BottomBarScreen("events", R.string.events_screen, Icons.AutoMirrored.Filled.List)

        data object Profile :
            BottomBarScreen("settings", R.string.profile_screen, Icons.Filled.Person)
    }
}