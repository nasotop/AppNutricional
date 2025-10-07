package com.example.appnutricional

import android.view.View
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appnutricional.auth.data.User
import com.example.appnutricional.auth.domain.UserRepository
import com.example.appnutricional.auth.ui.login.LoginScreen
import com.example.appnutricional.auth.ui.login.LoginViewModel
import com.example.appnutricional.core.data.AppDatabase
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import com.example.appnutricional.home.ui.home.HomeScreen
import com.example.appnutricional.home.ui.home.HomeViewModel
import com.example.appnutricional.ui.theme.AppNutricionalTheme
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
@RunWith(AndroidJUnit4::class)
class HomeTest {

    lateinit var userRepo: UserRepository

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Before
    fun setup() {

        val db = AppDatabase.getDatabase(composeTestRule.activity.applicationContext)
        userRepo = db.userRepository()
        runBlocking {
            val userTest= User(0, "usuario", "prueba", "usuario@prueba.cl", "12345")
            userRepo.add(userTest)
        }

    }

    @Test
    fun loginScreen_displayGeneralButtons() {
        composeTestRule.onNodeWithTag("emailField").assertExists()
        composeTestRule.onNodeWithText("passwordField").assertExists()
        composeTestRule.onNodeWithText("submitButton").assertExists()
    }



}