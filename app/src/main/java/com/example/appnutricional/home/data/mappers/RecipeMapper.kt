package com.example.appnutricional.home.data.mappers

import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.home.data.Recipe

object RecipeMapper {
    fun toModel(recipe: Recipe): RecipeModel=
        RecipeModel(recipe.name, recipe.ingredients.map { ing -> IngredientMapper.toModel(ing) })

    fun toEntity(recipe: RecipeModel): Recipe=
        Recipe(name=recipe.name, ingredients =  recipe.ingredients.map { ing -> IngredientMapper.toEntity(ing) })

}