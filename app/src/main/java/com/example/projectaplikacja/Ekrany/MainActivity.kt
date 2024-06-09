package com.example.projectaplikacja.Ekrany

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectaplikacja.Models.RoomRecipe
import com.example.projectaplikacja.Viewmodels.RoomViewModel
import com.example.projectaplikacja.Viewmodels.RoomViewModelFactory
import com.example.projectaplikacja.Viewmodels.SortType
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val viewModel: RoomViewModel = viewModel(
                factory = RoomViewModelFactory(context.applicationContext as Application)
            )
            ProjectAplikacjaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(context, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(context: Context, viewModel: RoomViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val recipes by viewModel.recipesState.collectAsState()


    LaunchedEffect(searchText) {
        viewModel.setFilter(searchText)
    }

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Podstawowe przepisy") },
                    navigationIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            if (context is MainActivity) {
                                context.finish()
                            }}) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Login"
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
                        RecipeItem(recipe, context)
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
                    DropdownMenuItem(
                        text = { Text(text = "Alfabetycznie") },
                        onClick = {
                            viewModel.setSortType(SortType.ALPHABETICAL)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Czas przygotowania rosnąco") },
                        onClick = {
                            viewModel.setSortType(SortType.PREPARATION_TIME_ASC)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Czas przygotowania malejąco") },
                        onClick = {
                            viewModel.setSortType(SortType.PREPARATION_TIME_DESC)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Rodzaj kuchni alfabetycznie") },
                        onClick = {
                            viewModel.setSortType(SortType.CUISINE_ALPHABETICAL)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Tylko wegetariańskie") },
                        onClick = {
                            viewModel.setSortType(SortType.VEGETARIAN_ONLY)
                            expanded = false
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchText,
                            onValueChange = { newText ->
                                searchText = newText
                                viewModel.setFilter(newText)
                            },
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
    }
}

@Composable
fun RecipeItem(recipe: RoomRecipe, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { openRecipeDetailsActivity(context, recipe.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(text = "Czas przygotowania: ${recipe.preparationTime} minut")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Rodzaj kuchni: ${recipe.cuisine}",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

fun openRecipeDetailsActivity(context: Context, recipeId: Int) {
    val intent = Intent(context, RecipeDetailsActivity1::class.java)
    intent.putExtra("RECIPE_ID", recipeId)
    context.startActivity(intent)

    if (context is MainActivity) {
        context.finish()
    }
}
