package com.example.appnutricional.home.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object IngredientTypeConverter {
    private val gson = Gson()
    private val type = object : TypeToken<List<Ingredient>>() {}.type

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<Ingredient>?): String =
        gson.toJson(list ?: emptyList<Ingredient>(), type)

    @TypeConverter
    @JvmStatic
    fun toList(json: String?): List<Ingredient> =
        if (json.isNullOrBlank()) emptyList() else gson.fromJson(json, type)
}