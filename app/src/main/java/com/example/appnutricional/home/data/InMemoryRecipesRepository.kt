package com.example.appnutricional.home.data

import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.IngredientType
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.core.extension.toNormalized
import com.example.appnutricional.home.data.mappers.IngredientMapper
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository

class InMemoryRecipesRepository(
    private val ingredientRepo: IngredientsRepository

)  {
    val recipes = mutableListOf(
        RecipeModel(
            name = "Ensalada fresca",
            ingredients = listOf(
                IngredientModel("Tomate", IngredientType.VEGETABLES),
                IngredientModel("Lechuga", IngredientType.VEGETABLES),
                IngredientModel("Aceite de oliva", IngredientType.FATS)
            )
        ),
        RecipeModel(
            name = "Arroz con pollo",
            ingredients = listOf(
                IngredientModel("Arroz", IngredientType.GRAINS),
                IngredientModel("Pollo", IngredientType.MEATS),
                IngredientModel("Tomate", IngredientType.VEGETABLES)
            )
        ),
        RecipeModel(
            name = "Batido de frutas",
            ingredients = listOf(
                IngredientModel("Leche", IngredientType.DAIRY),
                IngredientModel("Manzana", IngredientType.FRUITS),
                IngredientModel("Plátano", IngredientType.FRUITS)
            )
        ),
        RecipeModel(
            name = "Té dulce",
            ingredients = listOf(
                IngredientModel("Té verde", IngredientType.BEVERAGES),
                IngredientModel("Azúcar", IngredientType.SWEETENERS)
            )
        )
    ).map(::normalizedOrThrow)
        .toMutableList()

     fun getAll(): List<RecipeModel> = recipes.toList()

     fun findByName(name: String): RecipeModel? =
        recipes.firstOrNull {
            it
                .name
                .toNormalized()
                .equals(
                    name
                        .toNormalized(),
                    ignoreCase = true
                )
        }

     fun listByIngredientName(ingredientName: String): List<RecipeModel> =
        recipes.filter { recipe ->
            recipe
                .ingredients
                .any {
                    it
                        .name
                        .toNormalized()
                        .equals(
                            ingredientName
                                .toNormalized(),
                            ignoreCase = true
                        )
                }
        }

     fun add(recipe: RecipeModel): Boolean {
        if (recipes.any {
                it
                    .name
                    .toNormalized()
                    .equals(
                        recipe
                            .name
                            .toNormalized(),
                        ignoreCase = true
                    )
            }) return false
        val canon = normalizeRecipe(recipe) ?: return false
        recipes.add(canon)
        return true
    }

     fun update(name: String, newRecipe: RecipeModel): Boolean {
        val index = recipes.indexOfFirst { it.name.equals(name, ignoreCase = true) }
        if (index == -1) return false
        val canon = normalizeRecipe(newRecipe) ?: return false
        recipes[index] = canon
        return true
    }

     fun delete(name: String): Boolean {
        val it = recipes.iterator()
        var removed = false
        while (it.hasNext()) {
            if (it
                    .next()
                    .name
                    .toNormalized()
                    .equals(
                        name
                            .toNormalized(),
                        ignoreCase = true
                    )
            ) {
                it.remove()
                removed = true
            }
        }
        return removed
    }

    private fun normalizeRecipe(recipe: RecipeModel): RecipeModel? {
        val canon = recipe.ingredients.mapNotNull { wanted ->
            ingredientRepo.findByName(wanted.name)
        }
        if (canon.size != recipe.ingredients.size) return null
        return recipe.copy(ingredients = canon.map { ing-> IngredientMapper.toModel(ing) })
    }

    private fun normalizedOrThrow(recipe: RecipeModel): RecipeModel =
        normalizeRecipe(recipe)
            ?: error("Seed inválida: ingrediente no encontrado en IngredientRepository")

}