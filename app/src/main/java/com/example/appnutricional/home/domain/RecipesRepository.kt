package com.example.appnutricional.home.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appnutricional.core.domain.RecipeModel
import com.example.appnutricional.home.data.Recipe

@Dao
interface RecipesRepository {
    @Query("Select * from recipe")
    fun getAll(): List<Recipe>
    @Query("Select * from recipe where name=:name limit 1")

    fun findByName(name: String): Recipe?
    @Query("SELECT * FROM recipe WHERE ingredients LIKE '%' || :ingredientName || '%'")

    fun listByIngredientName(ingredientName: String): List<Recipe>
    @Insert(onConflict = OnConflictStrategy.ABORT)

    fun add(recipe: Recipe)
    @Update(onConflict = OnConflictStrategy.ABORT)

    fun update( newRecipe: Recipe): Int
    @Delete
     fun delete(recipe: Recipe): Int
    @Query("SELECT COUNT(*) FROM recipe")
     fun count(): Int

}