package com.lumen1024.data

internal inline fun <T> tryCatchDerived(exceptionHeader: String, block: () -> T): Result<T> {
    return try {
        return Result.success(block())

    } catch (e: Exception) {
        val message = "$exceptionHeader: " + (e.message ?: "no exception message provided")
        return Result.failure(Exception(message))
    }
}