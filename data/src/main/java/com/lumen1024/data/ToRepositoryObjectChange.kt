package com.lumen1024.data

import com.lumen1024.domain.data.RepositoryObjectChange

inline fun <reified T> DocumentChange.toRepositoryObjectChange(): RepositoryObjectChange<T?> {
    val type = when (this.type) {
        DocumentChange.Type.ADDED -> com.lumen1024.domain.RepositoryObjectChange.Type.ADDED
        DocumentChange.Type.MODIFIED -> com.lumen1024.domain.RepositoryObjectChange.Type.MODIFIED
        DocumentChange.Type.REMOVED -> com.lumen1024.domain.RepositoryObjectChange.Type.REMOVED
    }
    val data = this.document.toObject<T>()

    return RepositoryObjectChange(type, data)
}