package com.example.appnutricional.home.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnutricional.core.data.AppDatabase
import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.home.data.mappers.IngredientMapper
import com.example.appnutricional.home.data.mappers.RecipeMapper
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import com.example.appnutricional.home.domain.matchRecipes
import com.example.appnutricional.home.domain.validateNewRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    context: Context
) : ViewModel() {

    private val db = AppDatabase.getDatabase(context)
    private val ingredientRepo = db.ingredientRepository()
    private val recipeRepo = db.recipeRepository()
    private val _ui = MutableStateFlow(
        HomeUiState(
            allIngredients = emptyList(),
            recipes = emptyList()
        )
    )
    val ui: StateFlow<HomeUiState> = _ui

    init {
        loadInitial()
    }

    private fun loadInitial() {
        viewModelScope.launch {
            val ingEntities = withContext(Dispatchers.IO) { ingredientRepo.getAll() }
            val recEntities = withContext(Dispatchers.IO) { recipeRepo.getAll() }

            _ui.update {
                it.copy(
                    allIngredients = ingEntities.map(IngredientMapper::toModel),
                    recipes = recEntities.map(RecipeMapper::toModel)
                )
            }
            recompute()
        }
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

    fun createRecipe(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val s = ui.value
            val trimmed = s.newRecipeName.trim()

            val exists = withContext(Dispatchers.IO) { recipeRepo.findByName(trimmed) != null }

            val err = validateNewRecipe(
                name = trimmed,
                selected = s.selected,
                recipeExists = { exists }
            )
            if (err != null) {
                onResult(err)
                return@launch
            }

            val newRecipeModel = RecipeModel(trimmed, s.selected)
            val entity = RecipeMapper.toEntity(newRecipeModel)

            val insertedOk = withContext(Dispatchers.IO) {
                val res = recipeRepo.add(entity)
                when (res) {
                    is Long -> res > 0L
                    is Int  -> res > 0
                    else    -> false
                }
            }

            if (!insertedOk) {
                onResult("No se pudo crear")
                return@launch
            }

            _ui.update { it.copy(recipes = it.recipes + newRecipeModel, newRecipeName = "") }
            recompute()
            onResult(null)
        }
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
        } else base

        val matching = matchRecipes(s.selected, s.recipes)

        _ui.update { it.copy(filtered = filtered, matching = matching) }
    }

}