package com.example.appnutricional.auth.ui.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnutricional.auth.domain.AuthValidators
import com.example.appnutricional.auth.data.mappers.UserMapper
import com.example.appnutricional.core.data.AppDatabase
import com.example.appnutricional.core.domain.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set


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

    fun submitLogin(
        context: Context,
        onSuccess: (user: UserModel) -> Unit,
        onError: (String) -> Unit
    ) {
        if (uiState.isSubmitting) return

        validate()
        uiState = uiState.copy(showErrors = true)
        if (!uiState.isValid) return

        val email = uiState.email.trim()
        val password = uiState.password
        uiState = uiState.copy(isSubmitting = true)

        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)

                // IO work
                val currentUser = withContext(Dispatchers.IO) {
                    db.userRepository().findByCredentials(email, password)
                }

                if (currentUser == null) {
                    uiState = uiState.copy(isSubmitting = false)
                    onError("Credenciales inválidas")
                    return@launch
                }

                val prefs = context.getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE)
                prefs.edit {
                    putBoolean("logged_in", true)
                    putString("logged_user_email", currentUser.email)
                    putString("logged_user_name", currentUser.names+ " " + currentUser.lastNames)
                    putLong("login_timestamp", System.currentTimeMillis())
                }

                val userModel = UserMapper.toModel(currentUser)
                uiState = uiState.copy(isSubmitting = false)
                onSuccess(userModel)

            } catch (e: Exception) {
                uiState = uiState.copy(isSubmitting = false)
                onError("Error en login: ${e.message ?: "desconocido"}")
            }
        }
    }

}