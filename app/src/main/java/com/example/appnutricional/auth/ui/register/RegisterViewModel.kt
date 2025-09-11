package com.example.appnutricional.auth.ui.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appnutricional.auth.data.InMemoryUserRepository
import com.example.appnutricional.auth.domain.AuthValidators
import com.example.appnutricional.auth.domain.UserRepository
import com.example.appnutricional.core.domain.DuplicateUserException
import com.example.appnutricional.core.domain.UserModel
import com.example.appnutricional.core.extension.toNormalized
import com.example.appnutricional.core.util.runCatchingInline

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
        val emailError = AuthValidators.validateEmail(uiState.email)
        val passwordError = AuthValidators.validatePassword(uiState.password)

        val repeatPasswordError =
            AuthValidators.validateRepeatPassword(uiState.password, uiState.repeatPassword)
        val namesError = AuthValidators.validateStringBlankField(uiState.names, "nombre")
        val lastNamesError = AuthValidators.validateStringBlankField(uiState.lastNames, "apellido")

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
        if (!uiState.isValid) {
            onError("Modelo inválido")
            return
        }

        uiState = uiState.copy(isSubmitting = true)

        val email = uiState.email.toNormalized()
        val userToCreate = UserModel(
            names = uiState.names,
            lastNames = uiState.lastNames,
            email = email,
            password = uiState.password
        )

        val added: Boolean? = runCatchingInline(
            block = {
                userRepository.findByEmail(email)?.let {
                    throw DuplicateUserException(email)
                }
                userRepository.add(userToCreate)
            },
            onError = { t ->
                when (t) {
                    is DuplicateUserException -> {
                        uiState = uiState.copy(
                            showErrors = true,
                            emailError = "El correo ya se encuentra registrado"
                        )
                        onError("Correo ya registrado")
                    }

                    else -> {
                        onError("Error inesperado: ${t.message ?: "desconocido"}")
                    }
                }
            }
        )

        uiState = uiState.copy(isSubmitting = false)

        if (added == true) {
            onSuccess(true)
        } else if (added == false) {
            onError("No se pudo registrar el usuario")
        }

    }


}