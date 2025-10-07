package com.example.appnutricional.home.data.mappers

import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.home.data.Ingredient

object IngredientMapper {
    fun toModel(ingredient: Ingredient): IngredientModel=
        IngredientModel(ingredient.name, ingredient.type)
    fun toEntity(ingredient: IngredientModel): Ingredient=
        Ingredient(name=ingredient.name, type = ingredient.type)
}