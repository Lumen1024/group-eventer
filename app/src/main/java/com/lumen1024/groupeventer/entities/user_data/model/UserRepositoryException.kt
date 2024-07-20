package com.lumen1024.groupeventer.entities.user_data.model

import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.shared.model.RepositoryException

class UserRepositoryException(code: Code, message: String?) :
    RepositoryException(code, message)

fun UserRepositoryException.toMessage(): Int {
    return when (this.code) {
        RepositoryException.Code.OK -> R.string.group_aborted
        RepositoryException.Code.ALREADY_EXISTS -> R.string.group_already_exists
        RepositoryException.Code.CANCELLED -> R.string.canceled
        RepositoryException.Code.INVALID_ARGUMENT -> R.string.group_invalid_argument
        RepositoryException.Code.PERMISSION_DENIED -> R.string.group_permission_denied
        RepositoryException.Code.UNAUTHENTICATED -> R.string.unauthenticated
        RepositoryException.Code.UNAVAILABLE -> R.string.group_unavailable
        RepositoryException.Code.UNKNOWN -> R.string.group_unknown
        else -> R.string.group_unknown
    }
}