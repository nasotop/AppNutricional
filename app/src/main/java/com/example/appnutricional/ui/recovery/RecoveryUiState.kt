package com.example.appnutricional.ui.recovery

data class RecoveryUiState (
    val email: String = "",
    val emailError: String? = null,
    var isValid: Boolean = false,
    val showErrors: Boolean = false,
    val isSubmitting: Boolean = false
)