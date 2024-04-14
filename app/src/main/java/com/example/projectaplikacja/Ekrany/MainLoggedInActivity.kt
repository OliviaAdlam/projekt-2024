package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class MainLoggedInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent2(this)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent2(context: Context) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

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
                        IconButton(onClick = { /* Handle profile action click */ }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                // Content of your app goes here
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(recipes.size) { index ->
                        val recipe = recipes[index]
                        RecipeItem2(recipe,context)
                    }
                }
            }
        )

        if (expanded) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentSize() // Make the Box only wrap its content
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem( text = {Text(text = "Alfabetycznie")},
                        onClick = { /* Handle menu item click */ }
                    )
                    DropdownMenuItem( text = {Text(text = "Czas przygotowania rosnąco")},
                        onClick = { /* Handle menu item click */ }
                    )
                    DropdownMenuItem( text = {Text(text = "Czas przygotowania malejąco")},
                        onClick = { /* Handle menu item click */ }
                    )
                    DropdownMenuItem( text = {Text(text = "Rodzaj kuchni alfabetycznie")},
                        onClick = { /* Handle menu item click */ }
                    )
                    DropdownMenuItem( text = {Text(text = "Tylko wegetariańskie")},
                        onClick = { /* Handle menu item click */ }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
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
                onClick = { val intent = Intent(context,AddPositionActivity::class.java)
                    context.startActivity(intent) }
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
            .clickable { openRecipeDetailsActivityLoggedIn(context, recipe.id ) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineLarge, // Using h6 style for bigger and bold text
            )
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

fun openRecipeDetailsActivityLoggedIn(context: Context, recipeId: Int) {
    val intent = Intent(context, LoggedInRecipeDetailsActivity1::class.java)
    intent.putExtra("RECIPE_ID", recipeId)
    context.startActivity(intent)
}



