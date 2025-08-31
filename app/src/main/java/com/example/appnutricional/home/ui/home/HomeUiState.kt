package com.example.appnutricional.home.ui.home

import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.RecipeModel


data class HomeUiState(
    val query: String = "",
    val allIngredients: List<IngredientModel> = emptyList(),
    val filtered: List<IngredientModel> = emptyList(),
    val selected: List<IngredientModel> = emptyList(),
    val recipes: List<RecipeModel> = emptyList(),
    val matching: List<RecipeModel> = emptyList(),
    val newRecipeName: String = "",
)
