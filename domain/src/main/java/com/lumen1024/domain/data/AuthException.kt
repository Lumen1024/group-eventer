package com.lumen1024.domain.data

sealed class AuthException(message: String?) : Exception(message) {
    class WrongFormatEmail(message: String?) : AuthException(message)
    class ShortPassword(message: String?) : AuthException(message)
    class IncorrectCredentials(message: String?) : AuthException(message)
    class EmailAlreadyExist(message: String?) : AuthException(message)
    class Unknown(message: String?) : AuthException(message)
}
