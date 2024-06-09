package com.example.projectaplikacja.Viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectaplikacja.Models.RecipeDatabase
import com.example.projectaplikacja.Models.RoomRecipe
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RoomViewModel(application: Application): ViewModel() {

    private val repository: RoomRepository
    private val _recipeState = MutableStateFlow<List<RoomRecipe>>(emptyList())
    val recipesState: StateFlow<List<RoomRecipe>>
        get() = _recipeState

    private val _singleRecipeState = MutableStateFlow<RoomRecipe?>(null)
    val singleRecipeState: StateFlow<RoomRecipe?>
        get() = _singleRecipeState

    private val _filter = MutableStateFlow<String>("")
    val filter: StateFlow<String> = _filter

    private val _sortType = MutableStateFlow<SortType>(SortType.NONE)
    val sortType: StateFlow<SortType> = _sortType

    init {
        val db = RecipeDatabase.getDatabase(application)
        val dao = db.recipeDao()
        repository = RoomRepository(dao)
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            combine(_filter, _sortType) { filter, sortType ->
                when (sortType) {
                    SortType.ALPHABETICAL -> repository.getRecipesSortedByName()
                    SortType.PREPARATION_TIME_ASC -> repository.getRecipesSortedByPreparationTimeAsc()
                    SortType.PREPARATION_TIME_DESC -> repository.getRecipesSortedByPreparationTimeDesc()
                    SortType.CUISINE_ALPHABETICAL -> repository.getRecipesSortedByCuisine()
                    SortType.VEGETARIAN_ONLY -> repository.getVegetarianRecipes()
                    SortType.NONE -> repository.getFilteredRecipes(filter)
                }
            }.flatMapLatest { it }.collect { recipes ->
                _recipeState.value = recipes
            }
        }
    }

    fun fetchRecipeById(recipeId: Int) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)
            _singleRecipeState.value = recipe
        }
    }

    fun setFilter(filter: String) {
        _filter.value = filter
    }

    fun setSortType(sortType: SortType) {
        _sortType.value = sortType
    }
}

enum class SortType {
    NONE,
    ALPHABETICAL,
    PREPARATION_TIME_ASC,
    PREPARATION_TIME_DESC,
    CUISINE_ALPHABETICAL,
    VEGETARIAN_ONLY
}
