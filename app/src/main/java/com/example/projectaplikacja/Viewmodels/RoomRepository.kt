package com.example.projectaplikacja.Viewmodels

import com.example.projectaplikacja.Models.RoomRecipe
import com.example.projectaplikacja.Models.RoomRecipeDao
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val roomRecipeDao: RoomRecipeDao) {

    val allRecipes: Flow<List<RoomRecipe>> = roomRecipeDao.getAllRecipes()

    suspend fun getRecipeById(recipeId: Int): RoomRecipe? {
        return roomRecipeDao.getRecipeById(recipeId)
    }

    fun getFilteredRecipes(filter: String): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getFilteredRecipes(filter)
    }

    fun getRecipesSortedByName(): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getRecipesSortedByName()
    }

    fun getRecipesSortedByPreparationTimeAsc(): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getRecipesSortedByPreparationTimeAsc()
    }

    fun getRecipesSortedByPreparationTimeDesc(): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getRecipesSortedByPreparationTimeDesc()
    }

    fun getRecipesSortedByCuisine(): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getRecipesSortedByCuisine()
    }

    fun getVegetarianRecipes(): Flow<List<RoomRecipe>> {
        return roomRecipeDao.getVegetarianRecipes()
    }
}
