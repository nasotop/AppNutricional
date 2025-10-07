package com.example.appnutricional.home.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import com.example.appnutricional.ui.components.TopBar
import com.example.appnutricional.ui.theme.AppNutricionalTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.appnutricional.home.data.InMemoryIngredientsRepository
import com.example.appnutricional.home.data.InMemoryRecipesRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onGoBack: () -> Unit,
    onRecipeChosen: (RecipeModel) -> Unit
) {
    val state by vm.ui.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                title = "Home",
                showBack = true,
                onBack = onGoBack,
                onSettings = null,
                onMenu = null
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Buscar ingrediente
            OutlinedTextField(
                value = state.query,
                onValueChange = vm::onQueryChange,
                label = { Text("Buscar ingrediente") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Resultados", style = MaterialTheme.typography.titleMedium)
            IngredientResults(
                items = state.filtered,
                selected = state.selected,
                onToggle = vm::toggleIngredient,
            )

            Text("Seleccionados", style = MaterialTheme.typography.titleMedium)
            SelectedIngredients(
                selected = state.selected,
                onRemove = vm::toggleIngredient
            )

            Text("Recetas compatibles", style = MaterialTheme.typography.titleMedium)
            MatchingRecipesList(
                recipes = state.matching,
                onUse = onRecipeChosen
            )

            // Crear receta
            Text("Crear receta nueva", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = state.newRecipeName,
                onValueChange = vm::onNewRecipeNameChange,
                label = { Text("Nombre de la receta") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    vm.createRecipe { err ->
                        scope.launch {
                            snackbar.showSnackbar(err ?: "Receta creada")
                        }
                        if (err == null && state.matching.none { it.name.equals(state.newRecipeName, true) }) {
                            // si necesitas navegar, hazlo aquí usando el último estado ya recomputado
                            // onRecipeChosen(state.recipes.last())
                        }
                    }
                },
                enabled = state.newRecipeName.isNotBlank() && state.selected.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear receta")
            }
        }
    }
}

@Composable
private fun IngredientResults(
    items: List<IngredientModel>,
    selected: List<IngredientModel>,
    onToggle: (IngredientModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 250.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        items(items, key = { it.name }) { ing ->
            val isAdded = selected.any { it.name.equals(ing.name, true) }
            ListItem(
                headlineContent = { Text(ing.name) },
                supportingContent = { Text(ing.type) },
                trailingContent = {
                    val label = if (isAdded) "Quitar" else "Agregar"
                    TextButton(onClick = { onToggle(ing) }) { Text(label) }
                }
            )
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectedIngredients(
    selected: List<IngredientModel>,
    onRemove: (IngredientModel) -> Unit
) {
    if (selected.isEmpty()) {
        Text("No has agregado ingredientes.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        return
    }
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        selected.forEach { ing ->
            AssistChip(
                onClick = { onRemove(ing) },
                label = { Text(ing.name) }
            )
        }
    }
}

@Composable
private fun MatchingRecipesList(
    recipes: List<RecipeModel>,
    onUse: (RecipeModel) -> Unit
) {
    if (recipes.isEmpty()) {
        Text(
            "Ninguna receta coincide con la selección actual.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp, max = 200.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        items(recipes, key = { it.name }) { recipe ->
            ListItem(
                headlineContent = { Text(recipe.name) },
                supportingContent = { Text(recipe.ingredients.joinToString { it.name }) },
                trailingContent = { Button(onClick = { onUse(recipe) }) { Text("Usar") } }
            )
            HorizontalDivider()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAppScaffold() {
    AppNutricionalTheme(
        useDarkTheme = null,
        useDynamicColor = false
    ) {
        val context = LocalContext.current
        val vm = remember(context) { HomeViewModel(context) }
        HomeScreen(vm, onGoBack = {}, onRecipeChosen = {})
    }
}
