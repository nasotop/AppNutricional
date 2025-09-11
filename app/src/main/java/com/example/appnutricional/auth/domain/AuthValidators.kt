package com.example.appnutricional.auth.domain

import android.util.Patterns
import com.example.appnutricional.core.extension.isEmailLike

object AuthValidators {
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo no puede estar vacío"
            !email.isEmailLike -> "Formato de email inválido"
            else -> null
        }
    }
    fun validatePassword(password: String): String? {
       return when {
            password.isBlank() -> "La contraseña no puede estar vacía"
            else -> null
        }
    }
    fun validateRepeatPassword(password:String, passwordRepeat:String): String?{
        return when {
            passwordRepeat.isBlank() -> "La contraseña no puede estar vacía"
            passwordRepeat != password -> "Las contraseñas deben ser iguales"
            else -> null
        }
    }
    fun validateStringBlankField(field:String, fieldName: String):String?{
        return  when {
            field.isBlank() -> "El $fieldName no puede estar vacío"
            else -> null
        }
    }
}