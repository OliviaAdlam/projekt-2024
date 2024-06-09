package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projectaplikacja.Models.AuthManager
import com.example.projectaplikacja.Models.Recipe
import com.example.projectaplikacja.Viewmodels.FirebaseViewModel
import com.example.projectaplikacja.Viewmodels.FirebaseViewModelFactory
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class MainLoggedInActivity : ComponentActivity() {
    private val authManager = AuthManager(this)
    private val viewModel: FirebaseViewModel by viewModels {
        FirebaseViewModelFactory(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent2(this, authManager, viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent2(context: Context, authManager: AuthManager, viewModel: FirebaseViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val recipes by viewModel.recipesState.collectAsState()

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Twoje przepisy") },
                    navigationIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        // Add actions here
                        IconButton(onClick = { authManager.signOut()
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(recipes.size) { index ->
                        val recipe = recipes[index]
                        RecipeItem2(recipe, context)
                    }
                }
            }
        )

        if (expanded) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentSize()
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(text = { Text(text = "Alfabetycznie") },
                        onClick = { viewModel.fetchRecipesSortedByName(); expanded = false }
                    )
                    DropdownMenuItem(text = { Text(text = "Czas przygotowania rosnąco") },
                        onClick = { viewModel.fetchRecipesSortedByPreparationTime(true); expanded = false }
                    )
                    DropdownMenuItem(text = { Text(text = "Czas przygotowania malejąco") },
                        onClick = { viewModel.fetchRecipesSortedByPreparationTime(false); expanded = false }
                    )
                    DropdownMenuItem(text = { Text(text = "Tylko wegetariańskie") },
                        onClick = { viewModel.fetchVegetarianRecipes(); expanded = false }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it
                                viewModel.searchRecipes(searchText)},
                            placeholder = {
                                Text("Szukaj")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = { val intent = Intent(context, AddPositionActivity::class.java)
                    context.startActivity(intent)
                    if (context is MainLoggedInActivity) {
                        context.finish()
                    }}
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}





@Composable
fun RecipeItem2(recipe: Recipe, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { openRecipeDetailsActivityLoggedIn(context, recipe.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineLarge, // Using h6 style for bigger and bold text
                )
                if (recipe.favourite) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Favourite",
                        tint = Color.Yellow,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Text(text = "Czas przygotowania: ${recipe.preparationTime} minut")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f)) // Spacer wypełniający dostępną przestrzeń
                Text(
                    text = "Rodzaj kuchni: ${recipe.cuisine}",
                    modifier = Modifier.padding(end = 8.dp) // Odstęp od prawej strony
                )
            }
        }
    }
}

fun openRecipeDetailsActivityLoggedIn(context: Context, recipeId: String) {
    Log.d("Id", recipeId)
    val intent = Intent(context, LoggedInRecipeDetailsActivity1::class.java)
    intent.putExtra("RECIPE_ID", recipeId)
    context.startActivity(intent)
    if (context is MainActivity) {
        context.finish()
    }
}



