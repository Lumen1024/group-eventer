package com.lumen1024.groupeventer.entities.auth.model

import com.google.firebase.auth.FirebaseAuthException
import com.lumen1024.groupeventer.R

interface AuthService {

    fun checkAuthorized(): Boolean

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(
        email: String,
        name: String,
        password: String,
    ): Result<Unit>

    suspend fun logout()
}


sealed class AuthException(message: String?) : Throwable(message) {
    class WrongFormatEmail(message: String?) : AuthException(message)
    class ShortPassword(message: String?) : AuthException(message)
    class IncorrectCredentials(message: String?) : AuthException(message)
    class EmailAlreadyExist(message: String?) : AuthException(message)
    class Unknown(message: String?) : AuthException(message)
}

fun FirebaseAuthException.toAuthException() : AuthException
{
    return when (this.errorCode) {
        "ERROR_INVALID_EMAIL" ->
            AuthException.WrongFormatEmail(this.message)

        "ERROR_WEAK_PASSWORD" ->
            AuthException.ShortPassword(this.message)

        "ERROR_EMAIL_ALREADY_IN_USE" -> AuthException.EmailAlreadyExist(this.message)

        "ERROR_INVALID_CREDENTIAL" ->
            AuthException.IncorrectCredentials(this.message)

        else -> AuthException.Unknown(this.message)
    }
}

fun AuthException.mapAuthExceptionToMessage(): Int {
    return when (this) {
        is AuthException.WrongFormatEmail -> R.string.wrong_format_email
        is AuthException.IncorrectCredentials -> R.string.incorrect_credential
        is AuthException.ShortPassword -> R.string.short_password
        is AuthException.Unknown -> R.string.unknown_auth_exception
        is AuthException.EmailAlreadyExist -> R.string.email_already_exist
    }
}
