package com.example.appnutricional.home.domain

import com.example.appnutricional.core.domain.RecipeModel

interface RecipesRepository {
    fun getAll(): List<RecipeModel>
    fun findByName(name: String): RecipeModel?
    fun listByIngredientName(ingredientName: String): List<RecipeModel>
    fun add(recipe: RecipeModel): Boolean
    fun update(name: String, newRecipe: RecipeModel): Boolean
    fun delete(name: String): Boolean
}