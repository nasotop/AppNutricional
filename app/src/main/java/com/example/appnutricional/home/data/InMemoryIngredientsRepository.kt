package com.example.appnutricional.home.data

import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.IngredientType
import com.example.appnutricional.core.extension.toNormalized
import com.example.appnutricional.home.domain.IngredientsRepository

class InMemoryIngredientsRepository : IngredientsRepository {
    private val ingredients = mutableListOf(
        IngredientModel("Tomate", IngredientType.VEGETABLES),
        IngredientModel("Lechuga", IngredientType.VEGETABLES),
        IngredientModel("Manzana", IngredientType.FRUITS),
        IngredientModel("Plátano", IngredientType.FRUITS),
        IngredientModel("Pollo", IngredientType.MEATS),
        IngredientModel("Leche", IngredientType.DAIRY),
        IngredientModel("Arroz", IngredientType.GRAINS),
        IngredientModel("Aceite de oliva", IngredientType.FATS),
        IngredientModel("Azúcar", IngredientType.SWEETENERS),
        IngredientModel("Té verde", IngredientType.BEVERAGES)
    )

    override fun getAll(): List<IngredientModel> = ingredients.toList()

    override fun findByName(name: String): IngredientModel? =
        ingredients.firstOrNull {
            it.name.toNormalized().equals(name.toNormalized(), ignoreCase = true)
        }

    override fun listByType(type: String): List<IngredientModel> =
        ingredients.filter { it.type == type }

    override fun add(ingredient: IngredientModel): Boolean {
        if (ingredients.any {
                it
                    .name
                    .toNormalized()
                    .equals(
                        ingredient
                            .name
                            .toNormalized(),
                        ignoreCase = true
                    )
            })
            return false
        ingredients.add(ingredient)
        return true
    }

    override fun update(name: String, newIngredient: IngredientModel): Boolean {
        val index = ingredients.indexOfFirst {
            it
                .name
                .toNormalized()
                .equals(
                    name
                        .toNormalized(),
                    ignoreCase = true
                )
        }
        if (index == -1) return false
        ingredients[index] = newIngredient
        return true
    }

    override fun delete(name: String): Boolean {
        val iterator = ingredients.iterator()
        var removed = false
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item
                    .name
                    .toNormalized()
                    .equals(
                        name
                            .toNormalized(),
                        ignoreCase = true
                    )
            ) {
                iterator.remove()
                removed = true
            }
        }
        return removed
    }

}