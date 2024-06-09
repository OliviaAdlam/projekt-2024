package com.example.projectaplikacja.Models

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseInitializer {
    fun prepopulate(database: RecipeDatabase) {
        val recipeDao = database.recipeDao()
        CoroutineScope(Dispatchers.IO).launch {

            val initialRecipes = listOf(
                RoomRecipe(
                    name = "Spaghetti Bolognese",
                    steps = "1. Ugotuj spaghetti. 2. Przygotuj sos. 3. Wymieszaj razem.",
                    preparationTime = 30,
                    cuisine = "Włoska",
                    ingredients = "Spaghetti 250g, mielone mięso 400g, sos pomidorowy 200g, cebula, 2 ząbki czosnku",
                    isVegeterian = false
                ),
                RoomRecipe(
                    name = "Kurczak curry",
                    steps = "1. Ugotuj kurczaka. 2. Dodaj sos curry. 3. Gotuj.",
                    preparationTime = 40,
                    cuisine = "Tajska",
                    ingredients = "Kurczak 400g, proszek curry 1 łyżka, mleko kokosowe 250ml, cebula, czosnek, imbir",
                    isVegeterian = false
                ),
                RoomRecipe(
                    name = "Rolki sushi",
                    steps = "1. Przygotuj ryż. 2. Dodaj nadzienia. 3. Zwijaj ciasno.",
                    preparationTime = 50,
                    cuisine = "Japońska",
                    ingredients = "Ryż do sushi 1 szklanka, nori szczypta, awokado 1szt, ogórek 1szt",
                    isVegeterian = true
                ),
                RoomRecipe(
                    name = "Tacos",
                    steps = "1. Ugotuj mięso. 2. Przygotuj dodatki. 3. Złóż tacos.",
                    preparationTime = 20,
                    cuisine = "Meksykańska",
                    ingredients = "Tortille, mielone mięso 400g, sałata, ser, salsa",
                    isVegeterian = false
                ),
                RoomRecipe(
                    name = "Naleśniki",
                    steps = "1. Wymieszaj składniki. 2. Smaż na patelni. 3. Podawaj z syropem.",
                    preparationTime = 15,
                    cuisine = "Amerykańska",
                    ingredients = "Mąka 250g, 2 jajka, mleko 100ml, proszek do pieczenia, cukier 50g, masło 50g",
                    isVegeterian = true
                )
            )
            recipeDao.insertAll(initialRecipes)
            Log.d("DatabaseInitializer", "Baza danych została wypełniona początkowymi przepisami.")
        }
    }
}
