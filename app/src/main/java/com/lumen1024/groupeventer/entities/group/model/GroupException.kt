package com.lumen1024.groupeventer.entities.group.model

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.*
import com.lumen1024.groupeventer.R

sealed class GroupException(message: String?) : Throwable(message) {
    class Aborted(message: String?) : GroupException(message)
    class AlreadyExists(message: String?) : GroupException(message)
    class Canceled(message: String?) : GroupException(message)
    class InvalidArgument(message: String?) : GroupException(message)
    class PermissionDenied(message: String?) : GroupException(message)
    class Unauthenticated(message: String?) : GroupException(message)
    class Unavailable(message: String?) : GroupException(message)
    class Unknown(message: String?) : GroupException(message)
}

fun FirebaseFirestoreException.toGroupException(): GroupException {
    return when (this.code) {
        ABORTED -> GroupException.Aborted(this.message)
        ALREADY_EXISTS -> GroupException.AlreadyExists(this.message)
        CANCELLED -> GroupException.Canceled(this.message)
        DATA_LOSS -> GroupException.Unknown(this.message)
        DEADLINE_EXCEEDED -> GroupException.Unknown(this.message)
        FAILED_PRECONDITION -> GroupException.Unknown(this.message)
        INTERNAL -> GroupException.Unknown(this.message)
        INVALID_ARGUMENT -> GroupException.InvalidArgument(this.message)
        NOT_FOUND -> GroupException.Unknown(this.message)
        OUT_OF_RANGE -> GroupException.Unknown(this.message)
        PERMISSION_DENIED -> GroupException.PermissionDenied(this.message)
        RESOURCE_EXHAUSTED -> GroupException.Unknown(this.message)
        UNAUTHENTICATED -> GroupException.Unauthenticated(this.message)
        UNAVAILABLE -> GroupException.Unavailable(this.message)
        UNIMPLEMENTED -> GroupException.Unknown(this.message)
        UNKNOWN -> GroupException.Unknown(this.message)
        else -> GroupException.Unknown(this.message)
    }
}

fun GroupException.mapGroupExceptionToMessage(): Int {
    return when (this) {
        is GroupException.Aborted -> R.string.group_aborted
        is GroupException.AlreadyExists -> R.string.group_already_exists
        is GroupException.Canceled -> R.string.canceled
        is GroupException.InvalidArgument -> R.string.group_invalid_argument
        is GroupException.PermissionDenied -> R.string.group_permission_denied
        is GroupException.Unauthenticated -> R.string.unauthenticated
        is GroupException.Unavailable -> R.string.group_unavailable
        is GroupException.Unknown -> R.string.group_unknown
    }
}