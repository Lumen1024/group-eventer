package com.lumen1024.ui.screen.auth.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.usecase.LoginUserUseCase
import com.lumen1024.domain.usecase.RegisterUserUseCase
import com.lumen1024.ui.lib.navigator.Navigator
import com.lumen1024.ui.lib.toast.ToastService
import com.lumen1024.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val toastService: ToastService,
    private val navigator: Navigator,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()

    fun onAction(action: AuthUiAction) {
        when (action) {
            AuthUiAction.OnConfirmClicked -> onConfirmClicked()
            is AuthUiAction.OnAuthMethodChanged -> onAuthMethodChanged(action.method)
            is AuthUiAction.OnEmailChanged -> onEmailChanged(action.email)
            is AuthUiAction.OnNameChanged -> onNameChanged(action.name)
            is AuthUiAction.OnPasswordChanged -> onPasswordChanged(action.password)
            AuthUiAction.OnGoogleClicked -> onGoogleClicked()
        }
    }

    private fun onConfirmClicked() {
        when (state.value.authMethod) {
            AuthMethod.Login ->
                with(state.value) { login(email, password) }

            AuthMethod.Register ->
                with(state.value) { register(name, email, password) }
        }
    }

    private fun onAuthMethodChanged(method: AuthMethod) {
        _state.value = _state.value.copy(authMethod = method)
    }

    private fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    private fun onNameChanged(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    private fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onGoogleClicked() {
        login("test@test.test", "12345678") // TODO: remove on release
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val isSuccess = loginUserUseCase(email, password).isSuccess
            _state.value = _state.value.copy(isLoading = false)
            if (isSuccess) navigator.navigate(Screen.Events)
            else toastService.showToast("Error when logging in")
        }
    }

    private fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val isSuccess = registerUserUseCase(name, email, password).isSuccess
            _state.value = _state.value.copy(isLoading = false)
            if (isSuccess) navigator.navigate(Screen.Events)
            else toastService.showToast("Error when registering")
        }
    }
}