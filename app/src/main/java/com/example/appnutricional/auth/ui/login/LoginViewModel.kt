package com.example.appnutricional.auth.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appnutricional.auth.domain.AuthValidators
import com.example.appnutricional.auth.data.InMemoryUserRepository
import com.example.appnutricional.core.domain.UserModel

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set
    private var userRepository = InMemoryUserRepository()


    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailError = null)
        validate()
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = null)
        validate()

    }

    fun onRememberCredentialsChange(rememberCredentials: Boolean) {
        uiState =
            uiState.copy(rememberCredentials = rememberCredentials, rememberCredentialsError = null)
        validate()

    }

    fun validate() {
        val emailError = AuthValidators.validateEmail(uiState.email)
        val passwordError = AuthValidators.validatePassword(uiState.password)
        val valid = emailError == null && passwordError == null

        uiState = uiState.copy(
            emailError = emailError,
            passwordError = passwordError,
            isValid = valid
        )
    }

    fun submitLogin(onSuccess: (user: UserModel) -> Unit, onError: (String) -> Unit) {
        if (uiState.isSubmitting) return

        validate()
        uiState = uiState.copy(showErrors = true)
        if (!uiState.isValid) return

        uiState = uiState.copy(isSubmitting = true)

        try {
            val currentUser = userRepository.findByCredentials(
                uiState.email.trim(),
                uiState.password
            )

            if (currentUser == null) {

                uiState = uiState.copy(isSubmitting = false)

                onError("Credenciales invalidas")

                return
            }
            onSuccess(currentUser)

        } catch (e: Exception) {
            onError("Credenciales invalidas")
        } finally {
            uiState = uiState.copy(isSubmitting = false)

        }

    }

}