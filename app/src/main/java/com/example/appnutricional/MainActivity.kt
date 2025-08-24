package com.example.appnutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appnutricional.ui.theme.AppNutricionalTheme
import com.example.appnutricional.routes.routes
import com.example.appnutricional.ui.login.LoginApp
import com.example.appnutricional.ui.recovery.RecoveryApp
import com.example.appnutricional.ui.register.RegisterApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNutricionalTheme(
                useDarkTheme = null,
                useDynamicColor = false
            ) {

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavApp()
                }
            }
        }
    }
}

@Composable
fun NavApp() {
    var navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = routes.LOGIN
    ) {
        composable(routes.LOGIN) {
            LoginApp(
                onGoRecovery = { navController.navigate(routes.RECOVERY) },
                onGoRegister = { navController.navigate(routes.REGISTER) }
            )
        }
        composable(routes.RECOVERY) {
            RecoveryApp({ navController.popBackStack() })
        }

        composable(routes.REGISTER) {
            RegisterApp({ navController.popBackStack() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppScaffold() {
    AppNutricionalTheme (
        useDarkTheme = null,
        useDynamicColor = false
    ){ LoginApp(onGoRegister = {}, onGoRecovery = {}) }
}