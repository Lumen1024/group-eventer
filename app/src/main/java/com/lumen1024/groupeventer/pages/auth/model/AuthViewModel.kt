package com.lumen1024.groupeventer.pages.auth.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.handleExceptionWithToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    @ApplicationContext val context: Context
) : ViewModel() {

    lateinit var navController: NavController

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun handleLogin(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            handleExceptionWithToast(context) {
                authService.login(email, password)
                navController.navigate(Screen.Home.route)
            }
            _isLoading.value = false
        }
    }

    fun handleRegister(email: String, name: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            handleExceptionWithToast(context) {
                authService.register(email, name, password)
                navController.navigate(Screen.Home.route)
            }
            _isLoading.value = false
        }
    }
}