package com.example.appnutricional.auth.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.*
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appnutricional.ui.components.TopBar


@Composable
fun RegisterScreen(
    onGoBack: () -> Unit,
    onGoLogin: ()-> Unit,
    vm: RegisterViewModel
) {
    val state = vm.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = "Registrarse",
                showBack = true,
                onBack = onGoBack,
                onSettings = null,
                onMenu = null
            )
        },
        floatingActionButton = {}
    )
    { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .semantics {
                    contentDescription =
                        "Pantalla de registro. Completa los campos para crear tu cuenta."
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Únete a nuestra comunidad",
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { heading() }
                )

                Text(
                    text = "Completa el formulario. Todos los campos son obligatorios.",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                )


                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.names,
                    onValueChange = vm::onNameChange,
                    label = { Text("Nombres") },
                    placeholder = { Text("Tu nombre") },
                    isError = state.showErrors && state.namesError != null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (state.showErrors && state.namesError != null) {
                            Text(
                                text = state.namesError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics { liveRegion = LiveRegionMode.Assertive }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                )
                OutlinedTextField(
                    value = state.lastNames,
                    onValueChange = vm::onLastNameChange,
                    label = { Text("Apellidos") },
                    placeholder = { Text("Tu apellido") },
                    isError = state.showErrors && state.lastnamesError != null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (state.showErrors && state.lastnamesError != null) {
                            Text(
                                text = state.lastnamesError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics { liveRegion = LiveRegionMode.Assertive }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = vm::onEmailChange,
                    label = { Text("Correo") },
                    placeholder = { Text("correo@ejemplo.cl") },
                    isError = state.showErrors && state.emailError != null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (state.showErrors && state.emailError != null) {
                            Text(
                                text = state.emailError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics { liveRegion = LiveRegionMode.Assertive }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    placeholder = { Text("Mínimo 8 caracteres") },
                    isError = state.showErrors && state.passwordError != null,
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    supportingText = {
                        if (state.showErrors && state.passwordError != null) {
                            Text(
                                text = state.passwordError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics { liveRegion = LiveRegionMode.Assertive }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                )
                OutlinedTextField(
                    value = state.repeatPassword,
                    onValueChange = vm::onPasswordRepeatChange,
                    label = { Text("Repetir Contraseña") },
                    placeholder = { Text("Repite tu contraseña") },
                    isError = state.showErrors && state.repeatPasswordError != null,
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    supportingText = {
                        if (state.showErrors && state.repeatPasswordError != null) {
                            Text(
                                text = state.repeatPasswordError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics { liveRegion = LiveRegionMode.Assertive }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                )

                Button(
                    onClick = {
                        vm.submitRegister(
                            onSuccess = {
                                success ->
                                if(success)
                                    onGoLogin()
                                else
                                    Toast.makeText(context, "Ocurrio un error al crear el usuario", Toast.LENGTH_LONG).show()

                            },
                            onError = {msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()


                            })
                    },
                    enabled = !state.isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                ) {
                    Text(if (state.isSubmitting) "Enviando..." else "Registrarse")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}