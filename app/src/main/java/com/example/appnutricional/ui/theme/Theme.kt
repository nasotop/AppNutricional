package com.example.appnutricional.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColors  = darkColorScheme(
    primary = TerracottaLight,
    onPrimary = Color.White,
    primaryContainer = Terracotta,
    onPrimaryContainer = Color.White,

    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = Color.White,

    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = Color.White,

    background = Charcoal,
    onBackground = Color(0xFFECECEC),
    surface = Color(0xFF161A1D),
    onSurface = Color(0xFFECECEC),
    surfaceVariant = Color(0xFF21262B),
    onSurfaceVariant = Color(0xFFDFDFDF),

    error = Danger,
    onError = Color.White,
    outline = Color(0xFF9AA0A6),
)

private val LightColors  = lightColorScheme(
    primary = Terracotta,
    onPrimary = Color.White,
    primaryContainer = TerracottaLight,
    onPrimaryContainer = Color.White,

    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = Color.White,

    tertiary = Tertiary,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = Color.White,

    background = OffWhite,
    onBackground = Color(0xFF121212),
    surface = Sand,
    onSurface = Ink,
    surfaceVariant = Color(0xFFE7DBC8),
    onSurfaceVariant = Ink,

    error = Danger,
    onError = Color.White,
    outline = Color(0xFF555555),


)

@Composable
fun AppNutricionalTheme(
    useDarkTheme: Boolean? = null,
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val darkTheme = useDarkTheme ?: systemDark
    val context = LocalContext.current

    val colorScheme =
        if (useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // definido abajo
        content = content
    )
}