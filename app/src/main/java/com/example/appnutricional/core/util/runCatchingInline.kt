package com.example.appnutricional.core.util

inline fun <T> runCatchingInline(
    crossinline block: () -> T,
    crossinline onError: (Throwable) -> Unit
): T? {
    return try {
        block()
    } catch (t: Throwable) {
        onError(t)
        null
    }
}