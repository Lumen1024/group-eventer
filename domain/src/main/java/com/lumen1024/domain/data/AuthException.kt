package com.lumen1024.domain.data

sealed class AuthException(message: String?) : Throwable(message) {
    class WrongFormatEmail(message: String?) : AuthException(message)
    class ShortPassword(message: String?) : AuthException(message)
    class IncorrectCredentials(message: String?) : AuthException(message)
    class EmailAlreadyExist(message: String?) : AuthException(message)
    class Unknown(message: String?) : AuthException(message)
}

//fun FirebaseAuthException.toAuthException(): AuthException {
//    return when (this.errorCode) {
//        "ERROR_INVALID_EMAIL" ->
//            com.lumen1024.domain.data.AuthException.WrongFormatEmail(this.message)
//
//        "ERROR_WEAK_PASSWORD" ->
//            com.lumen1024.domain.data.AuthException.ShortPassword(this.message)
//
//        "ERROR_EMAIL_ALREADY_IN_USE" -> com.lumen1024.domain.data.AuthException.EmailAlreadyExist(this.message)
//
//        "ERROR_INVALID_CREDENTIAL" ->
//            com.lumen1024.domain.data.AuthException.IncorrectCredentials(this.message)
//
//        else -> com.lumen1024.domain.data.AuthException.Unknown(this.message)
//    }
//}

//fun AuthException.mapToResource(): Int {
//    return when (this) {
//        is AuthException.WrongFormatEmail -> R.string.wrong_format_email
//        is AuthException.IncorrectCredentials -> R.string.incorrect_credential
//        is AuthException.ShortPassword -> R.string.short_password
//        is AuthException.Unknown -> R.string.unknown_auth_exception
//        is AuthException.EmailAlreadyExist -> R.string.email_already_exist
//    }
//}
