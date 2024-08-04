package com.lumen1024.groupeventer.shared.lib

/**
 * Returns a single map of all elements in keys and parent in value
 * yielded from results of [transform] function being invoked on each element of original collection.
 */
inline fun <T, R> Iterable<R>.flatMapLinked(transform: (R) -> Iterable<T>): Map<T, R> {
    return flatMap { parent ->
        transform(parent).map { item ->
            item to parent
        }
    }.toMap()
}