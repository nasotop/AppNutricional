package com.example.appnutricional.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberCredentials: Boolean=false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val rememberCredentialsError: String? = null,
    val isValid: Boolean = false,
    val showErrors: Boolean = false,
    val isSubmitting: Boolean = false
)