package com.lumen1024.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.lumen1024.presentation.R
import kotlinx.serialization.Serializable

interface HasIcon {
    val icon: ImageVector
}

interface HasLabel {
    @get:StringRes
    val label: Int
}


sealed interface Screen {
    @Serializable
    data object Auth : Screen

    @Serializable
    data object Tutorial : Screen

    @Serializable
    data object CreateEvent : Screen

    @Serializable
    data object Groups : Screen, HasLabel, HasIcon {
        override val icon: ImageVector = Icons.Default.Groups
        override val label: Int = R.string.groups_screen
    }

    @Serializable
    data object Events : Screen, HasLabel, HasIcon {
        override val icon: ImageVector = Icons.AutoMirrored.Filled.List
        override val label: Int = R.string.events_screen
    }

    @Serializable
    data object Profile : Screen, HasLabel, HasIcon {
        override val icon: ImageVector = Icons.Default.Person
        override val label: Int = R.string.profile_screen
    }
}

fun Screen.isEqualsToRoute(currentBackStackEntry: NavBackStackEntry?): Boolean {
    return this.javaClass.canonicalName == currentBackStackEntry?.destination?.route
}