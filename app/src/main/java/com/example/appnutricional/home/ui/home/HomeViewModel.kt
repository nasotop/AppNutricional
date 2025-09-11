package com.example.appnutricional.home.ui.home

import androidx.lifecycle.ViewModel
import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import com.example.appnutricional.home.domain.matchRecipes
import com.example.appnutricional.home.domain.validateNewRecipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val ingredientRepo: IngredientsRepository,
    private val recipeRepo: RecipesRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(
        HomeUiState(
            allIngredients = ingredientRepo.getAll(),
            recipes = recipeRepo.getAll()
        )
    )
    val ui: StateFlow<HomeUiState> = _ui

    init {
        recompute()
    }

    fun onQueryChange(q: String) {
        _ui.update { it.copy(query = q) }
        recompute()
    }

    fun toggleIngredient(ing: IngredientModel) {
        val cur = _ui.value
        val selected = if (cur.selected.any { it.name.equals(ing.name, true) }) {
            cur.selected.filterNot { it.name.equals(ing.name, true) }
        } else cur.selected + ing
        _ui.update { it.copy(selected = selected) }
        recompute()
    }

    fun onNewRecipeNameChange(name: String) {
        _ui.update { it.copy(newRecipeName = name) }
    }

    /**
     * Crea la receta si pasa validación. Devuelve null si OK, o mensaje de error si falla.
     */
    fun createRecipe(): String? {
        val s = _ui.value
        val err = validateNewRecipe(
            name = s.newRecipeName.trim(),
            selected = s.selected,
            recipeExists = { n -> recipeRepo.findByName(n) != null }
        )
        if (err != null) return err

        val newRecipe = RecipeModel(s.newRecipeName.trim(), s.selected)
        val ok = recipeRepo.add(newRecipe)
        if (!ok) return "No se pudo crear"

        _ui.update { it.copy(
            recipes = it.recipes + newRecipe,
            newRecipeName = ""
        )}
        recompute()
        return null
    }

    private fun recompute() {
        val s = _ui.value

        val base = if (s.query.isBlank()) {
            s.allIngredients
        } else {
            s.allIngredients.filter { it.name.contains(s.query, ignoreCase = true) }
        }


        var exactIndex = -1
        base.forEachIndexed { idx, ing ->
            if (ing.name.equals(s.query, ignoreCase = true)) {
                exactIndex = idx
                return@forEachIndexed
            }
        }

        val filtered = if (exactIndex >= 0) {
            val head = base[exactIndex]
            val tail = base.filterIndexed { i, _ -> i != exactIndex }
            listOf(head) + tail
        } else {
            base
        }

        val matching = matchRecipes(s.selected, s.recipes)

        _ui.update { it.copy(filtered = filtered, matching = matching) }
    }

}