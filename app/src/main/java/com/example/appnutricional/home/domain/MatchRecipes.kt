package com.example.appnutricional.home.domain

import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.core.extension.toNormalized

fun matchRecipes(selected: List<IngredientModel>, recipes: List<RecipeModel>): List<RecipeModel> {
    if (selected.isEmpty()) return emptyList()
    val selectedNames = selected
        .map {
            it
                .name
                .toNormalized()
        }
        .toSet()
    return recipes.filter { recipe ->
        recipe
            .ingredients
            .all {
                it
                    .name
                    .toNormalized() in selectedNames
            }
    }
}