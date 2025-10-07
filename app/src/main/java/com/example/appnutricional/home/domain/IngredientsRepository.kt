package com.example.appnutricional.home.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.home.data.Ingredient
import com.example.appnutricional.home.data.Recipe

@Dao
interface IngredientsRepository {
    @Query("Select * from ingredient")

    fun getAll(): List<Ingredient>
    @Query("Select * from ingredient where name  = :name  limit 1")

    fun findByName(name: String): Ingredient?
    @Query("Select * from ingredient where type  = :type ")

    fun listByType(type: String): List<Ingredient>
    @Insert(onConflict = OnConflictStrategy.ABORT)

    fun add(ingredient: Ingredient)
    @Update(onConflict = OnConflictStrategy.ABORT)

    fun update( newIngredient: Ingredient): Int
    @Delete()

    fun delete(ingredient: Ingredient): Int

    @Query("SELECT COUNT(*) FROM ingredient")
     fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(ingredients: List<Ingredient>)

}