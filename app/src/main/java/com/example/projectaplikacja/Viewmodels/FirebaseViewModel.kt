package com.example.projectaplikacja.Viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectaplikacja.Models.FirebaseRecipeDao
import com.example.projectaplikacja.Models.Recipe
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirebaseViewModel(application: Application) : ViewModel() {

    private val repository: FirebaseRepository
    private val _recipeState = MutableStateFlow<List<Recipe>>(emptyList())
    val recipesState: StateFlow<List<Recipe>>
        get() = _recipeState

    private val _singleRecipeState = MutableStateFlow<Recipe?>(null)
    val singleRecipeState: StateFlow<Recipe?>
        get() = _singleRecipeState

    init {
        val recipeDao = FirebaseRecipeDao()
        repository = FirebaseRepository(recipeDao)
        fetchRecipes()
    }

    private fun fetchRecipes() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            repository.allRecipes(userId).collect { recipes ->
                _recipeState.value = recipes
                Log.d("FirebaseViewModel", "Fetched recipes: $recipes")
            }
        }
    }

    fun fetchRecipesSortedByName() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            repository.recipesSortedByName(userId).collect { recipes ->
                _recipeState.value = recipes
            }
        }
    }

    fun searchRecipes(query: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            repository.searchRecipes(userId, query).collect { recipes ->
                _recipeState.value = recipes
            }
        }
    }

    fun fetchRecipesSortedByPreparationTime(ascending: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            repository.recipesSortedByPreparationTime(userId, ascending).collect { recipes ->
                _recipeState.value = recipes
            }
        }
    }

    fun fetchVegetarianRecipes() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            repository.vegetarianRecipes(userId).collect { recipes ->
                _recipeState.value = recipes
            }
        }
    }

    fun fetchRecipeById(recipeId: String) {
        viewModelScope.launch {
            repository.getRecipeById(recipeId).collect { recipe ->
                _singleRecipeState.value = recipe
            }
        }
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
        }
    }

    fun deleteRecipe(recipeId: String) {
        viewModelScope.launch {
            repository.delete(recipeId)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.update(recipe)
            fetchRecipes()
        }
    }
}
