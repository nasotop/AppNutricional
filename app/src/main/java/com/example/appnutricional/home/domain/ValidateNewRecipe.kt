package com.example.appnutricional.home.domain

import com.example.appnutricional.core.domain.IngredientModel

fun validateNewRecipe(
    name: String,
    selected: List<IngredientModel>,
    recipeExists: (String) -> Boolean
): String? = when {
    name.isBlank() -> "Ingresa un nombre"
    selected.isEmpty() -> "Agrega al menos un ingrediente"
    recipeExists(name) -> "Ya existe una receta con ese nombre"
    else -> null
}