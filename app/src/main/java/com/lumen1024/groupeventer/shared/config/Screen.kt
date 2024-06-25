package com.lumen1024.groupeventer.shared.config

import androidx.annotation.StringRes
import com.lumen1024.groupeventer.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Auth : Screen("auth", R.string.auth)
    data object Splash : Screen("splash", R.string.auth)
    data object Tutorial : Screen("tutorial", R.string.tutorial)
    data object Home : Screen("home", R.string.home)

    data object Groups : Screen("profile", R.string.groups_screen)
    data object Events : Screen("events", R.string.events_screen)
    data object Profile : Screen("settings", R.string.profile_screen)
}