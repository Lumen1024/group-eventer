package com.lumen1024.groupeventer.shared.lib

inline fun <T, R> Iterable<R>.flatMapLinked(transform: (R) -> Iterable<T>): Map<T, R> {
    return flatMap { parent ->
        transform(parent).map { item ->
            item to parent
        }
    }.toMap()
}