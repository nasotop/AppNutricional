package com.example.appnutricional.ui.register


data class RegisterUiState(
    val names: String = "",
    val lastNames: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val namesError: String? = "",
    val lastnamesError: String? = "",
    val emailError: String? = "",
    val passwordError: String? = "",
    val repeatPasswordError: String? = "",
    val isValid: Boolean =false,
    val showErrors: Boolean = false,
    val isSubmitting: Boolean = false
)
