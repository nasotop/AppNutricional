package com.example.appnutricional.home.domain

import com.example.appnutricional.core.domain.IngredientModel

interface IngredientsRepository {
    fun getAll(): List<IngredientModel>
    fun findByName(name: String): IngredientModel?
    fun listByType(type: String): List<IngredientModel>
    fun add(ingredient: IngredientModel): Boolean
    fun update(name: String, newIngredient: IngredientModel): Boolean
    fun delete(name: String): Boolean

}