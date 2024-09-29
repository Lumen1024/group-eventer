package com.lumen1024.domain.data

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

