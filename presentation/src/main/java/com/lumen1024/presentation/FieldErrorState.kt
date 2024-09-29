package com.lumen1024.presentation

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
