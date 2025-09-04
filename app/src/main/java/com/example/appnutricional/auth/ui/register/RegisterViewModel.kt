package com.example.appnutricional.auth.ui.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appnutricional.auth.data.InMemoryUserRepository
import com.example.appnutricional.auth.domain.UserRepository
import com.example.appnutricional.core.domain.UserModel

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailError = null)
        validate()
    }

    fun onPasswordChange(newPass: String) {
        uiState = uiState.copy(password = newPass, passwordError = null)
        validate()
    }

    fun onNameChange(newName: String) {
        uiState = uiState.copy(names = newName, namesError = null)
        validate()
    }

    fun onLastNameChange(newLastname: String) {
        uiState = uiState.copy(lastNames = newLastname, lastnamesError = null)
        validate()
    }

    fun onPasswordRepeatChange(newRepeatPass: String) {
        uiState = uiState.copy(repeatPassword = newRepeatPass, repeatPasswordError = null)
        validate()
    }

    fun validate() {
        val emailError = when {
            uiState.email.isBlank() -> "El correo no puede estar vacío"
            !Patterns.EMAIL_ADDRESS.matcher(uiState.email)
                .matches() -> "Formato de email inválido"

            else -> null
        }
        val passwordError = when {
            uiState.password.isBlank() -> "La contraseña no puede estar vacía"
            uiState.password.length < 8 -> "Debe tener al menos 8 caracteres"
            else -> null
        }

        val repeatPasswordError = when {
            uiState.repeatPassword.isBlank() -> "La contraseña no puede estar vacía"
            uiState.repeatPassword != uiState.password -> "Las contraseñas deben ser iguales"
            else -> null
        }
        val namesError = when {
            uiState.names.isBlank() -> "El nombre no puede estar vacío"
            else -> null
        }
        val lastNamesError = when {
            uiState.lastNames.isBlank() -> "El apellido no puede estar vacío"
            else -> null
        }
        val valid =
            emailError == null && passwordError == null && repeatPasswordError == null && namesError == null && lastNamesError == null

        uiState = uiState.copy(
            emailError = emailError,
            passwordError = passwordError,
            repeatPasswordError = repeatPasswordError,
            namesError = namesError,
            lastnamesError = lastNamesError,
            isValid = valid
        )
    }

    fun submitRegister(onSuccess: (Boolean) -> Unit, onError: (String) -> Unit) {
        validate()



        uiState = uiState.copy(showErrors = true)

        if (!uiState.isValid) return onError("Modelo invalido")

        uiState = uiState.copy(isSubmitting = true)

        val user = UserModel(
            names = uiState.names,
            lastNames = uiState.lastNames,
            email = uiState.email,
            password = uiState.password
        )

        val usuario = userRepository.findByEmail(uiState.email)

        if (usuario != null) {
            uiState = uiState.copy(showErrors = true)
            uiState = uiState.copy(isSubmitting = false)
            uiState = uiState.copy(
                emailError = "El correo ya se encuentra registrado"
            )
            return onError("Modelo invalido")
        }
        val result = userRepository.add(user)

        uiState = uiState.copy(isSubmitting = false)

        onSuccess(result)
    }


}