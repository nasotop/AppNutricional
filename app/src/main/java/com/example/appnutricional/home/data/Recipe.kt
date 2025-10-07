package com.example.appnutricional.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.appnutricional.core.domain.IngredientModel

@Entity(tableName = "recipe")
@TypeConverters(IngredientTypeConverter::class)

data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val ingredients: List<Ingredient>
)