package com.example.appnutricional.ui.recovery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RecoveryViewModel: ViewModel() {
    var uiState by mutableStateOf(RecoveryUiState())
        private set
    fun onEmailChange(newEmail: String){
        uiState= uiState.copy(email = newEmail, emailError = null)
        validate()
    }
    fun validate() {
        val emailError= when {
            uiState.email.isBlank() ->"El correo no puede estar vacío"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches() -> "Formato de email inválido"
            else -> null
        }
        val valid = emailError == null

        uiState = uiState.copy(
            emailError = emailError,
            isValid = valid
        )
    }
    fun submitRecovery(onSuccess: () -> Unit, onError: (String) -> Unit) {
        validate()
        uiState = uiState.copy(showErrors = true)

        if (!uiState.isValid) return

        uiState = uiState.copy(isSubmitting = true)

        uiState = uiState.copy(isSubmitting = false)
        onSuccess()
    }

}