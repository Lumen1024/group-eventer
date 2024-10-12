package com.lumen1024.ui.shared.text_field

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
