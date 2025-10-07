package com.example.appnutricional.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName="ingredient")

data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val name: String,
    val type: String
)
