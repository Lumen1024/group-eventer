package com.lumen1024.groupeventer.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.lumen1024.groupeventer.data.AuthService
import com.lumen1024.groupeventer.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun handleLogin(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            handleExceptions {
                authService.login(email, password)
                navController.navigate(Screen.Home.route)
            }
            _isLoading.value = false
        }

    }

    fun handleRegister(email: String, name: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            handleExceptions {
                authService.register(email, name, password)
                navController.navigate(Screen.Home.route)
            }
            _isLoading.value = false
        }

    }

    private suspend fun handleExceptions(action: suspend () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            if (e is FirebaseException) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}