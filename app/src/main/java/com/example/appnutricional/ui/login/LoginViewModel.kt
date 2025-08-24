package com.example.appnutricional.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
var uiState by mutableStateOf(LoginUiState())
    private set

    fun onEmailChange(newEmail: String){
        uiState= uiState.copy(email = newEmail, emailError = null)
        validate()
    }
    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null)
        validate()

    }
    fun onRememberCredentialsChange(rememberCredentials: Boolean) {
        uiState = uiState.copy(rememberCredentials = rememberCredentials, rememberCredentialsError = null)
        validate()

    }

    fun validate() {
        val emailError = when {
            uiState.email.isBlank() -> "El correo no puede estar vacío"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches() -> "Formato de email inválido"
            else -> null
        }
        val passwordError = when {
            uiState.password.isBlank() -> "La contraseña no puede estar vacía"
            uiState.password.length < 8 -> "Debe tener al menos 8 caracteres"
            else -> null
        }
        val valid = emailError == null && passwordError == null

        uiState = uiState.copy(
            emailError = emailError,
            passwordError = passwordError,
            isValid = valid
        )
    }
    fun submitLogin(onSuccess: () -> Unit, onError: (String) -> Unit) {
        validate()
        uiState = uiState.copy(showErrors = true)

        if (!uiState.isValid) return

        uiState = uiState.copy(isSubmitting = true)

        uiState = uiState.copy(isSubmitting = false)
        onSuccess()
    }

}