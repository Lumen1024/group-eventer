package com.lumen1024.groupeventer.pages.auth.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.app.navigation.MainNavigator
import com.lumen1024.groupeventer.entities.auth.model.AuthException
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.auth.model.mapAuthExceptionToMessage
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val navigator: MainNavigator,
    private val authService: AuthService,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _passwordError = MutableStateFlow(PasswordErrorState.Normal)
    val passwordError = _passwordError.asStateFlow()

    private val _nameError = MutableStateFlow(NameErrorState.Normal)
    val nameError = _nameError.asStateFlow()

    private val _emailError = MutableStateFlow(EmailErrorState.Normal)
    val emailError = _emailError.asStateFlow()

    fun googleClicked() {
    }

    fun handleLogin(email: String, password: String) {
        resetErrors()
        if (!handleEmptyValues(email, password))
            return
        viewModelScope.launch {
            _isLoading.value = true

            val r = authService.login(email, password)

            if (r.isFailure) handleException(r.exceptionOrNull() as AuthException)
            else navigator.navigate(Screen.Home)

            _isLoading.value = false

        }
    }

    fun handleRegister(email: String, name: String, password: String) {
        resetErrors()
        if (!handleEmptyValues(email, password, name))
            return
        viewModelScope.launch {
            _isLoading.value = true

            val r = authService.register(email, name, password)
            if (r.isFailure) handleException(r.exceptionOrNull() as AuthException)
            else navigator.navigate(Screen.Home)

            _isLoading.value = false
        }
    }

    private fun handleEmptyValues(email: String, password: String, name: String? = null): Boolean {
        if (name != null && name.isEmpty()) {
            _nameError.value = NameErrorState.Empty
            return false
        }
        if (email.isEmpty()) {
            _emailError.value = EmailErrorState.Empty
            return false
        }
        if (password.isEmpty()) {
            _passwordError.value = PasswordErrorState.Empty
            return false
        }
        return true
    }

    private fun resetErrors() {
        _nameError.value = NameErrorState.Normal
        _emailError.value = EmailErrorState.Normal
        _passwordError.value = PasswordErrorState.Normal
    }

    private fun handleException(exception: AuthException) {
        when (exception) {
            is AuthException.EmailAlreadyExist -> _emailError.value = EmailErrorState.AlreadyExist
            is AuthException.IncorrectCredentials -> context.showToast(
                context.resources.getString(
                    exception.mapAuthExceptionToMessage()
                )
            )

            is AuthException.ShortPassword -> _passwordError.value = PasswordErrorState.Short
            is AuthException.Unknown -> context.showToast(context.resources.getString(exception.mapAuthExceptionToMessage()))
            is AuthException.WrongFormatEmail -> _emailError.value = EmailErrorState.WrongFormat
        }

    }
}