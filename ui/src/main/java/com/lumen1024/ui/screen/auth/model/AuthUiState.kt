package com.lumen1024.ui.screen.auth.model

import androidx.compose.runtime.Immutable

@Immutable
data class AuthUiState(
    val authMethod: AuthMethod = AuthMethod.Login,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)

@Immutable
sealed class AuthMethod {
    object Login : AuthMethod()
    object Register : AuthMethod()
}