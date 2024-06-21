package com.lumen1024.groupeventer.pages.auth.model

data class AuthUIState(
    val name: String = "",
    val email: String = "",
    val password: String = "",

    val isLogin: Boolean = true,
    val isLoading: Boolean = false,

    val nameErrorState: NameErrorState = NameErrorState.Normal,
    val passwordErrorState: PasswordErrorState = PasswordErrorState.Normal,
    val emailErrorState : EmailErrorState = EmailErrorState.Normal
)

enum class NameErrorState {
    Normal,
    Empty,
}

enum class PasswordErrorState {
    Normal,
    Empty,
    Short,
}

enum class EmailErrorState {
    Normal,
    Empty,
    WrongFormat,
    AlreadyExist
}