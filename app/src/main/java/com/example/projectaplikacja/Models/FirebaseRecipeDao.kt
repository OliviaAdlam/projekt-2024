package com.example.projectaplikacja.Models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import android.util.Log

class FirebaseRecipeDao : RecipeDao {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val recipesCollection: CollectionReference = firestore.collection("recipes")

    override fun addRecipe(recipe: Recipe): Task<Void> {
        val documentRef = if (recipe.id.isEmpty()) {
            recipesCollection.document()
        } else {
            recipesCollection.document(recipe.id)
        }
        return documentRef.set(recipe)
    }

    override fun deleteRecipe(recipeId: String): Task<Void> {
        return recipesCollection.document(recipeId).delete()
    }

    override fun getAllRecipe(userId: String): Task<List<Recipe>> {
        return recipesCollection.whereEqualTo("userId", userId).get().continueWith { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.mapNotNull { document ->
                    document.toObject(Recipe::class.java)?.let { recipe ->
                        Recipe(
                            id = document.id,
                            name = recipe.name,
                            steps = recipe.steps,
                            preparationTime = recipe.preparationTime,
                            cuisine = recipe.cuisine,
                            ingredients = recipe.ingredients,
                            vegeterian = recipe.vegeterian,
                            userId = recipe.userId,
                            favourite = recipe.favourite,
                        )
                    }
                } ?: emptyList()
            } else {
                Log.e("FirebaseRecipeDao", "Error getting recipes", task.exception)
                emptyList()
            }
        }
    }

    override fun getRecipeById(recipeId: String): Task<Recipe?> {
        return recipesCollection.document(recipeId).get().continueWith { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    document.toObject(Recipe::class.java)?.copy(id = recipeId)
                } else {
                    null
                }
            } else {
                Log.e("FirebaseRecipeDao", "Error getting recipe by ID", task.exception)
                null
            }
        }
    }

    override fun updateRecipe(recipe: Recipe): Task<Void> {
        return recipesCollection.document(recipe.id).set(recipe)
    }

    //
    fun getRecipesSortedByName(userId: String): Task<List<Recipe>> {
        return recipesCollection.whereEqualTo("userId", userId).orderBy("name").get().continueWith { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.mapNotNull { document ->
                    document.toObject(Recipe::class.java)?.let { recipe ->
                        Recipe(
                            id = document.id,
                            name = recipe.name,
                            steps = recipe.steps,
                            preparationTime = recipe.preparationTime,
                            cuisine = recipe.cuisine,
                            ingredients = recipe.ingredients,
                            vegeterian = recipe.vegeterian,
                            userId = recipe.userId,
                            favourite = recipe.favourite,
                        )
                    }
                } ?: emptyList()
            } else {
                Log.e("FirebaseRecipeDao", "Error getting recipes sorted by name", task.exception)
                emptyList()
            }
        }
    }

    fun getRecipesSortedByPreparationTime(userId: String, ascending: Boolean): Task<List<Recipe>> {
        val query: Query = if (ascending) {
            recipesCollection.whereEqualTo("userId", userId).orderBy("preparationTime")
        } else {
            recipesCollection.whereEqualTo("userId", userId).orderBy("preparationTime", Query.Direction.DESCENDING)
        }
        return query.get().continueWith { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.mapNotNull { document ->
                    document.toObject(Recipe::class.java)?.let { recipe ->
                        Recipe(
                            id = document.id,
                            name = recipe.name,
                            steps = recipe.steps,
                            preparationTime = recipe.preparationTime,
                            cuisine = recipe.cuisine,
                            ingredients = recipe.ingredients,
                            vegeterian = recipe.vegeterian,
                            userId = recipe.userId,
                            favourite = recipe.favourite,
                        )
                    }
                } ?: emptyList()
            } else {
                Log.e("FirebaseRecipeDao", "Error getting recipes sorted by preparation time", task.exception)
                emptyList()
            }
        }
    }

    fun getVegetarianRecipes(userId: String): Task<List<Recipe>> {
        return recipesCollection.whereEqualTo("userId", userId).whereEqualTo("vegeterian", true).get().continueWith { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.mapNotNull { document ->
                    document.toObject(Recipe::class.java)?.let { recipe ->
                        Recipe(
                            id = document.id,
                            name = recipe.name,
                            steps = recipe.steps,
                            preparationTime = recipe.preparationTime,
                            cuisine = recipe.cuisine,
                            ingredients = recipe.ingredients,
                            vegeterian = recipe.vegeterian,
                            userId = recipe.userId,
                            favourite = recipe.favourite,
                        )
                    }
                } ?: emptyList()
            } else {
                Log.e("FirebaseRecipeDao", "Error getting vegetarian recipes", task.exception)
                emptyList()
            }
        }
    }
    fun searchRecipes(userId: String, query: String): Task<List<Recipe>> {
        return recipesCollection
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("name", query)
            .whereLessThanOrEqualTo("name", query + "\uf8ff")
            .get().continueWith { task ->
                if (task.isSuccessful) {
                    val documents = task.result?.documents
                    documents?.mapNotNull { document ->
                        document.toObject(Recipe::class.java)?.let { recipe ->
                            Recipe(
                                id = document.id,
                                name = recipe.name,
                                steps = recipe.steps,
                                preparationTime = recipe.preparationTime,
                                cuisine = recipe.cuisine,
                                ingredients = recipe.ingredients,
                                vegeterian = recipe.vegeterian,
                                userId = recipe.userId,
                                favourite = recipe.favourite,
                            )
                        }
                    } ?: emptyList()
                } else {
                    Log.e("FirebaseRecipeDao", "Error searching recipes", task.exception)
                    emptyList()
                }
            }
    }
}
