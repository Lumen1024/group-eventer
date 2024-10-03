package com.lumen1024.data

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.toObject
import com.lumen1024.domain.data.RepositoryObjectChange

inline fun <reified T> DocumentChange.toRepositoryObjectChange(): RepositoryObjectChange<T?> {
    val type = when (this.type) {
        DocumentChange.Type.ADDED -> RepositoryObjectChange.Type.ADDED
        DocumentChange.Type.MODIFIED -> RepositoryObjectChange.Type.MODIFIED
        DocumentChange.Type.REMOVED -> RepositoryObjectChange.Type.REMOVED
    }
    val data = this.document.toObject<T>()

    return RepositoryObjectChange(type, data)
}