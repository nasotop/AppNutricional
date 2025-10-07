package com.example.appnutricional.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appnutricional.auth.data.User
import com.example.appnutricional.auth.domain.UserRepository
import com.example.appnutricional.core.domain.IngredientModel
import com.example.appnutricional.core.domain.IngredientType
import com.example.appnutricional.home.data.Ingredient
import com.example.appnutricional.home.data.Recipe
import com.example.appnutricional.home.domain.IngredientsRepository
import com.example.appnutricional.home.domain.RecipesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Ingredient::class, Recipe::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userRepository(): UserRepository
    abstract fun ingredientRepository(): IngredientsRepository
    abstract fun recipeRepository(): RecipesRepository
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(
                this
            ) {

                    context.deleteDatabase("app_db")
                val instance =
                    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_db")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        populateIfEmpty(
                                            ingredientDao = database.ingredientRepository(),
                                            recipeDao = database.recipeRepository()
                                        )
                                    }
                                }
                            }
                        })
                        .build()
                INSTANCE = instance
                instance
            }

        }
    }
}


fun populateIfEmpty(
    ingredientDao: IngredientsRepository,
    recipeDao: RecipesRepository
) {
    val ingredientCount = ingredientDao.count()
    if (ingredientCount == 0) {
        val ingredients = listOf(
            Ingredient(name = "Tomate",        type = IngredientType.VEGETABLES),
            Ingredient(name = "Lechuga",       type = IngredientType.VEGETABLES),
            Ingredient(name = "Manzana",       type = IngredientType.FRUITS),
            Ingredient(name = "Plátano",       type = IngredientType.FRUITS),
            Ingredient(name = "Pollo",         type = IngredientType.MEATS),
            Ingredient(name = "Leche",         type = IngredientType.DAIRY),
            Ingredient(name = "Arroz",         type = IngredientType.GRAINS),
            Ingredient(name = "Aceite de oliva", type = IngredientType.FATS),
            Ingredient(name = "Azúcar",        type = IngredientType.SWEETENERS),
            Ingredient(name = "Té verde",      type = IngredientType.BEVERAGES)
        )
        ingredientDao.insertAll(ingredients)
    }

    val recipeCount = recipeDao.count()
    if (recipeCount == 0) {
        val ensaladaFresca = Recipe(
            name = "Ensalada fresca",
            ingredients = listOf(
                Ingredient(name = "Tomate",        type = IngredientType.VEGETABLES),
                Ingredient(name = "Lechuga",       type = IngredientType.VEGETABLES),
                Ingredient(name = "Aceite de oliva", type = IngredientType.FATS)
            )
        )
        val arrozConPollo = Recipe(
            name = "Arroz con pollo",
            ingredients = listOf(
                Ingredient(name = "Arroz",   type = IngredientType.GRAINS),
                Ingredient(name = "Pollo",   type = IngredientType.MEATS),
                Ingredient(name = "Tomate",  type = IngredientType.VEGETABLES)
            )
        )
        val batidoDeFrutas = Recipe(
            name = "Batido de frutas",
            ingredients = listOf(
                Ingredient(name = "Leche",   type = IngredientType.DAIRY),
                Ingredient(name = "Manzana", type = IngredientType.FRUITS),
                Ingredient(name = "Plátano", type = IngredientType.FRUITS)
            )
        )
        val teDulce = Recipe(
            name = "Té dulce",
            ingredients = listOf(
                Ingredient(name = "Té verde", type = IngredientType.BEVERAGES),
                Ingredient(name = "Azúcar",   type = IngredientType.SWEETENERS)
            )
        )

        recipeDao.add(ensaladaFresca)
        recipeDao.add(arrozConPollo)
        recipeDao.add(batidoDeFrutas)
        recipeDao.add(teDulce)
    }
}