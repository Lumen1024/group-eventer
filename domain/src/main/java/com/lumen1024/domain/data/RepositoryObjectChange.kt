package com.lumen1024.domain.data

data class RepositoryObjectChange<T>(
    val type: Type,
    val data: T,
) {
    enum class Type {
        ADDED,
        MODIFIED,
        REMOVED
    }
}

