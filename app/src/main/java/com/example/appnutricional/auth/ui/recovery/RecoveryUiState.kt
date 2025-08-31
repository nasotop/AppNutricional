package com.example.appnutricional.auth.ui.recovery

data class RecoveryUiState (
    val email: String = "",
    val emailError: String? = null,
    var isValid: Boolean = false,
    val showErrors: Boolean = false,
    val isSubmitting: Boolean = false
)