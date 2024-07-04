package com.lumen1024.groupeventer.shared.model

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.toObject

class RepositoryObjectChange<T>(
    private val _type: Type,
    private val _data: T,
) {
    enum class Type {
        ADDED,
        MODIFIED,
        REMOVED
    }

    val type get() = this._type
    val data get() = this._data
}

inline fun <reified T> DocumentChange.toRepositoryObjectChange(): RepositoryObjectChange<T?> {
    val type = when (this.type) {
        DocumentChange.Type.ADDED -> RepositoryObjectChange.Type.ADDED
        DocumentChange.Type.MODIFIED -> RepositoryObjectChange.Type.MODIFIED
        DocumentChange.Type.REMOVED -> RepositoryObjectChange.Type.REMOVED
    }
    val data = this.document.toObject<T>()

    return RepositoryObjectChange(type, data)
}