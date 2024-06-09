package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectaplikacja.R
import com.example.projectaplikacja.Viewmodels.RoomViewModel
import com.example.projectaplikacja.Viewmodels.RoomViewModelFactory
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class RecipeDetailsActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val recipeId = intent.getIntExtra("RECIPE_ID", -1)
                    val viewModel: RoomViewModel = viewModel(
                        factory = RoomViewModelFactory(application)
                    )
                    AppContent6(this,recipeId,viewModel)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent6(context: Context, recipeId: Int, viewModel: RoomViewModel) {
    val recipe by viewModel.singleRecipeState.collectAsState()

    // Define a set to hold the indices of the checked checkboxes
    var checkedIndices by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeById(recipeId)
    }

    if (recipe != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = recipe!!.name) },
                            navigationIcon = {
                                IconButton(onClick = { context.startActivity(Intent(context, MainActivity::class.java))
                                    if (context is RecipeDetailsActivity1) {
                                        context.finish()
                                    }}) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Return")
                                }
                            },
                            actions = {
                                // Add actions here if needed
                            }
                        )
                    },
                    content = { paddingValues ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier.padding(paddingValues)
                            ) {
                                item {
                                    Text(text = "Składniki:")
                                    val ingredientsList = recipe!!.ingredients.split(", ")
                                    ingredientsList.forEachIndexed { index, ingredient ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = index in checkedIndices,
                                                onCheckedChange = { isChecked ->
                                                    // Update the set of checked indices
                                                    checkedIndices = if (isChecked) {
                                                        checkedIndices + index
                                                    } else {
                                                        checkedIndices - index
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = ingredient)
                                        }
                                    }
                                }
                            }

                            // Floating button at the bottom right corner
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                                    .background(color = Color.Gray)
                            ) {
                                IconButton(onClick = {
                                    val intent = Intent(context, RecipeDetailsActivity2::class.java)
                                    intent.putExtra("RECIPE_ID", recipeId)
                                    context.startActivity(intent)
                                    if (context is RecipeDetailsActivity1) {
                                        context.finish()
                                    }}) {
                                    Icon(Icons.Default.ArrowForward, contentDescription = "Add")
                                }
                            }
                        }
                    }
                )
            }
        }
    } else {
        // Handle case where the recipe is not found
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Przepis o ID $recipeId nie został znaleziony")
        }
    }
}

