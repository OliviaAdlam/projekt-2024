package com.example.projectaplikacja.Models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomRecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Int): RoomRecipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RoomRecipe>)

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :filter || '%' OR cuisine LIKE '%' || :filter || '%'")
    fun getFilteredRecipes(filter: String): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes ORDER BY name")
    fun getRecipesSortedByName(): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes ORDER BY preparationTime ASC")
    fun getRecipesSortedByPreparationTimeAsc(): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes ORDER BY preparationTime DESC")
    fun getRecipesSortedByPreparationTimeDesc(): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes ORDER BY cuisine")
    fun getRecipesSortedByCuisine(): Flow<List<RoomRecipe>>

    @Query("SELECT * FROM recipes WHERE isVegeterian = 1")
    fun getVegetarianRecipes(): Flow<List<RoomRecipe>>
}
