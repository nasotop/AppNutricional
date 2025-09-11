package com.example.appnutricional.core.extension

import android.util.Patterns

fun String.toNormalized(): String = trim().lowercase()

val String.isEmailLike: Boolean
    get()=Patterns.EMAIL_ADDRESS.matcher(this).matches()