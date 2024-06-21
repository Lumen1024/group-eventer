package com.lumen1024.groupeventer.pages.auth.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lumen1024.groupeventer.entities.auth.model.AuthException
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.auth.model.mapAuthExceptionToMessage
import com.lumen1024.groupeventer.shared.config.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService, @ApplicationContext val context: Context
) : ViewModel() {

    lateinit var navController: NavController

    private val _state = MutableStateFlow(AuthUIState())
    val state = _state.asStateFlow()

    fun onConfirmClicked() {
        if (_state.value.name.isEmpty().and(!state.value.isLogin)) _state.value =
            state.value.copy(nameErrorState = NameErrorState.Empty)
        if (_state.value.email.isEmpty()) _state.value =
            state.value.copy(emailErrorState = EmailErrorState.Empty)
        if (_state.value.password.isEmpty()) _state.value =
            state.value.copy(passwordErrorState = PasswordErrorState.Empty)

        if (haveProblems())
            return

        if (state.value.isLogin) handleLogin(state.value.email, state.value.password)
        else handleRegister(state.value.email, state.value.name, state.value.password)
    }

    fun onTabClicked(index: Int) {
        _state.value = state.value.copy(isLogin = (index == 0))
    }

    fun googleClicked() {

    }

    fun onNameEdit(value: String) {
        _state.value = state.value.copy(
            name = value, nameErrorState = NameErrorState.Normal
        )
    }

    fun onEmailEdit(value: String) {
        _state.value = state.value.copy(
            email = value, emailErrorState = EmailErrorState.Normal
        )
    }

    fun onPasswordEdit(value: String) {
        _state.value = state.value.copy(
            password = value, passwordErrorState = PasswordErrorState.Normal
        )
    }

    private fun handleLogin(email: String, password: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val r = authService.login(state.value.email, state.value.password)
            if (r.isFailure) handleException(r.exceptionOrNull() as AuthException)
            else navController.navigate(Screen.Home.route)
            _state.value = state.value.copy(isLoading = false)
        }
    }

    private fun handleRegister(email: String, name: String, password: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val r = authService.register(state.value.email, state.value.name, state.value.password)
            if (r.isFailure) handleException(r.exceptionOrNull() as AuthException)
            else navController.navigate(Screen.Home.route)
            _state.value = state.value.copy(isLoading = false)
        }
    }

    private fun handleException(exception: AuthException) {
        when (exception) {
            is AuthException.EmailAlreadyExist -> _state.value =
                state.value.copy(emailErrorState = EmailErrorState.AlreadyExist)

            is AuthException.IncorrectCredentials -> toast(context.resources.getString(exception.mapAuthExceptionToMessage()))
            is AuthException.ShortPassword -> _state.value =
                state.value.copy(passwordErrorState = PasswordErrorState.Short)

            is AuthException.Unknown -> toast(context.resources.getString(exception.mapAuthExceptionToMessage()))
            is AuthException.WrongFormatEmail -> _state.value =
                state.value.copy(emailErrorState = EmailErrorState.WrongFormat)
        }

    }


    private fun haveProblems(): Boolean {
        var result = (state.value.emailErrorState != EmailErrorState.Normal)
            .or(state.value.passwordErrorState != PasswordErrorState.Normal)
        if (!state.value.isLogin)
            result = result.or(state.value.nameErrorState != NameErrorState.Normal)
        return result
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}