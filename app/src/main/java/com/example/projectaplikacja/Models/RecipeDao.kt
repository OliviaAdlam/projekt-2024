package com.example.projectaplikacja.Models

import com.google.android.gms.tasks.Task

interface RecipeDao {
    fun addRecipe(recipe: Recipe): Task<Void>
    fun deleteRecipe(recipeId: String): Task<Void>
    fun getAllRecipe(userId: String): Task<List<Recipe>>
    fun updateRecipe(recipe: Recipe): Task<Void>
    fun getRecipeById(recipeId: String): Task<Recipe?>
}
