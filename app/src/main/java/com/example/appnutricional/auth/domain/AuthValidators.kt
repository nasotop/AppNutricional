package com.example.appnutricional.auth.domain

import android.util.Patterns

object AuthValidators {
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo no puede estar vacío"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de email inválido"
            else -> null
        }
    }
    fun validatePassword(password: String): String? {
       return when {
            password.isBlank() -> "La contraseña no puede estar vacía"
            else -> null
        }
    }
}