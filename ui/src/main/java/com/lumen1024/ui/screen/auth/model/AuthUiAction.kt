package com.lumen1024.ui.screen.auth.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AuthUiAction {
    data class OnAuthMethodChanged(val method: AuthMethod) : AuthUiAction
    data class OnNameChanged(val name: String) : AuthUiAction
    data class OnEmailChanged(val email: String) : AuthUiAction
    data class OnPasswordChanged(val password: String) : AuthUiAction

    object OnConfirmClicked : AuthUiAction
    object OnGoogleClicked : AuthUiAction
}