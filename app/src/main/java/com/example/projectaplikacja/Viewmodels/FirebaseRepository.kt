package com.example.projectaplikacja.Viewmodels

import com.example.projectaplikacja.Models.FirebaseRecipeDao
import com.example.projectaplikacja.Models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseRepository(private val recipeDao: FirebaseRecipeDao) {

    fun allRecipes(userId: String): Flow<List<Recipe>> = flow {
        val recipes = recipeDao.getAllRecipe(userId).await()
        emit(recipes)
    }

    fun recipesSortedByName(userId: String): Flow<List<Recipe>> = flow {
        val recipes = recipeDao.getRecipesSortedByName(userId).await()
        emit(recipes)
    }

    fun recipesSortedByPreparationTime(userId: String, ascending: Boolean): Flow<List<Recipe>> = flow {
        val recipes = recipeDao.getRecipesSortedByPreparationTime(userId, ascending).await()
        emit(recipes)
    }

    fun vegetarianRecipes(userId: String): Flow<List<Recipe>> = flow {
        val recipes = recipeDao.getVegetarianRecipes(userId).await()
        emit(recipes)
    }

    suspend fun insert(recipe: Recipe) {
        recipeDao.addRecipe(recipe).await()
    }

    fun searchRecipes(userId: String, query: String): Flow<List<Recipe>> = flow {
        val recipes = recipeDao.searchRecipes(userId, query).await()
        emit(recipes)
    }

    suspend fun delete(recipeId: String) {
        recipeDao.deleteRecipe(recipeId).await()
    }

    suspend fun getRecipeById(recipeId: String): Flow<Recipe?> = flow {
        val recipe = recipeDao.getRecipeById(recipeId).await()
        emit(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.updateRecipe(recipe).await()
    }
}
