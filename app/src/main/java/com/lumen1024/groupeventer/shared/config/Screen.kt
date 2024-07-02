package com.lumen1024.groupeventer.shared.config

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.lumen1024.groupeventer.R

interface HaveIcon {
    val icon: ImageVector
}
interface HaveLabel {
    @get:StringRes
    val label :  Int
}

sealed class Screen(open val route: String) {
    data object Auth : Screen("auth")
    data object Tutorial : Screen("tutorial")
    data object Home : Screen("home")
    {
        data object Groups : Screen("groups"), HaveLabel, HaveIcon {
            override val icon: ImageVector = Icons.Default.Groups
            override val label: Int = R.string.groups_screen
        }
        data object Events : Screen("Events"), HaveLabel, HaveIcon {
            override val icon: ImageVector = Icons.AutoMirrored.Filled.List
            override val label: Int = R.string.events_screen
        }
        data object Profile : Screen("settings"), HaveLabel, HaveIcon {
            override val icon: ImageVector = Icons.Default.Person
            override val label: Int = R.string.profile_screen
        }
    }
}