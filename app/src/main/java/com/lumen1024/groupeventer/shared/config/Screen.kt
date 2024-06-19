package com.lumen1024.groupeventer.shared.config

import androidx.annotation.StringRes
import com.lumen1024.groupeventer.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Auth : Screen("auth", R.string.auth)
    data object Splash : Screen("splash", R.string.auth)
    data object Tutorial : Screen("tutorial", R.string.tutorial)
    data object Home : Screen("home", R.string.home)

    data object EventList : Screen("events", R.string.event_list)
    data object MyTime : Screen("profile", R.string.my_time)
    data object Settings : Screen("settings", R.string.settings)
}