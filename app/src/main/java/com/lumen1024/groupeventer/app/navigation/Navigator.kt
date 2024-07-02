package com.lumen1024.groupeventer.app.navigation

import androidx.navigation.NavOptionsBuilder
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.shared.config.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

abstract class Navigator(val startDestination: Screen) {

    private val _destination = MutableStateFlow<String?>(null)
    val destination = _destination.asStateFlow()
    var builder: NavOptionsBuilder.() -> Unit = {}
    var popUpStart = false

    fun setDestination(screen: Screen) {
        _destination.value = screen.route
    }

    fun navigate(
        screen: Screen,
        popUpStart: Boolean = false,
        builder: NavOptionsBuilder.() -> Unit = {},
    ) {
        this.builder = builder
        this.popUpStart = popUpStart
        _destination.value = screen.route
    }
}

class MainNavigator @Inject constructor(authService: AuthService) :
    Navigator(if (authService.checkAuthorized()) Screen.Home else Screen.Auth)

class HomeNavigator @Inject constructor() : Navigator(Screen.Home.Events)