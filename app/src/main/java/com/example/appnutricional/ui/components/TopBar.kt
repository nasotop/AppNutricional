package com.example.appnutricional.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBack: Boolean,
    onBack: (() -> Unit)?,
    onSettings: (() -> Unit)? ,
    onMenu: (() -> Unit)?
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Tu Cocina - " + title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center

            )
        }

        ,
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { onBack?.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atras"
                    )
                }
            }
        },
        actions = {
            if (onSettings != null) {
                IconButton(onClick = { onSettings.invoke() }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Opciones")
                }
            }

            if (onMenu != null) {
                IconButton(onClick = { onMenu.invoke() }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
                }
            }
        },

        modifier = Modifier.fillMaxWidth()
    )
}