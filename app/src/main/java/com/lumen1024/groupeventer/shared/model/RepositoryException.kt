package com.lumen1024.groupeventer.shared.model

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.ABORTED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.ALREADY_EXISTS
import com.google.firebase.firestore.FirebaseFirestoreException.Code.CANCELLED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.DATA_LOSS
import com.google.firebase.firestore.FirebaseFirestoreException.Code.DEADLINE_EXCEEDED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.FAILED_PRECONDITION
import com.google.firebase.firestore.FirebaseFirestoreException.Code.INTERNAL
import com.google.firebase.firestore.FirebaseFirestoreException.Code.INVALID_ARGUMENT
import com.google.firebase.firestore.FirebaseFirestoreException.Code.NOT_FOUND
import com.google.firebase.firestore.FirebaseFirestoreException.Code.OUT_OF_RANGE
import com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAUTHENTICATED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNIMPLEMENTED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNKNOWN

open class RepositoryException(val code: Code, message: String? = null) :
    Throwable(message) {

    enum class Code {
        OK,
        CANCELLED,
        UNKNOWN,
        INVALID_ARGUMENT,
        ALREADY_EXISTS,
        PERMISSION_DENIED,
        ABORTED,
        UNIMPLEMENTED,
        UNAVAILABLE,
        UNAUTHENTICATED,
    }
}


fun FirebaseFirestoreException.toRepositoryException(): Throwable {
    return when (this.code) {
        ABORTED -> RepositoryException(RepositoryException.Code.ABORTED, this.message)
        ALREADY_EXISTS -> RepositoryException(
            RepositoryException.Code.ALREADY_EXISTS,
            this.message
        )

        CANCELLED -> RepositoryException(RepositoryException.Code.CANCELLED, this.message)
        DATA_LOSS -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        DEADLINE_EXCEEDED -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        FAILED_PRECONDITION -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        INTERNAL -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        INVALID_ARGUMENT -> RepositoryException(
            RepositoryException.Code.INVALID_ARGUMENT,
            this.message
        )

        NOT_FOUND -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        OUT_OF_RANGE -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        PERMISSION_DENIED -> RepositoryException(
            RepositoryException.Code.PERMISSION_DENIED,
            this.message
        )

        RESOURCE_EXHAUSTED -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        UNAUTHENTICATED -> RepositoryException(
            RepositoryException.Code.UNAUTHENTICATED,
            this.message
        )

        UNAVAILABLE -> RepositoryException(RepositoryException.Code.UNAVAILABLE, this.message)
        UNIMPLEMENTED -> RepositoryException(RepositoryException.Code.UNIMPLEMENTED, this.message)
        UNKNOWN -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
        else -> RepositoryException(RepositoryException.Code.UNKNOWN, this.message)
    }
}