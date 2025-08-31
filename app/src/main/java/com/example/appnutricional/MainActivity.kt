package com.example.appnutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appnutricional.ui.theme.AppNutricionalTheme
import com.example.appnutricional.navigation.Routes
import com.example.appnutricional.auth.ui.login.LoginScreen
import com.example.appnutricional.auth.ui.recovery.RecoveryScreen
import com.example.appnutricional.auth.ui.register.RegisterScreen
import com.example.appnutricional.home.data.InMemoryIngredientsRepository
import com.example.appnutricional.home.data.InMemoryRecipesRepository
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import com.example.appnutricional.home.ui.home.HomeScreen
import com.example.appnutricional.home.ui.home.HomeViewModel

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
    val navController = rememberNavController()
    val ingredientRepo: IngredientsRepository = InMemoryIngredientsRepository()
    val recipeRepo: RecipesRepository = InMemoryRecipesRepository(ingredientRepo)
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onGoRecovery = { navController.navigate(Routes.RECOVERY) },
                onGoRegister = { navController.navigate(Routes.REGISTER) },
                onGoHome = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.RECOVERY) {
            RecoveryScreen({ navController.popBackStack() })
        }

        composable(Routes.REGISTER) {
            RegisterScreen({ navController.popBackStack() })
        }
        composable(Routes.HOME) {
            val vm = remember { HomeViewModel(ingredientRepo, recipeRepo) }
            HomeScreen(
                vm,
                onGoBack = { navController.popBackStack() },
                onRecipeChosen = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppScaffold() {
    AppNutricionalTheme(
        useDarkTheme = null,
        useDynamicColor = false
    ) { LoginScreen(onGoRegister = {}, onGoRecovery = {}, onGoHome = {}) }
}