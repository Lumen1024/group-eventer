package com.lumen1024.groupeventer.app.navigation

import androidx.lifecycle.ViewModel
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.shared.config.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavGraphViewModel @Inject constructor(
    private val navigator: Navigator,
    private val authService: AuthService,
) : ViewModel() {

    val navigationChannel = navigator.navigationChannel

    fun getStartDestination(): Screen = if (authService.checkAuthorized())
        Screen.Events
    else
        Screen.Auth
}