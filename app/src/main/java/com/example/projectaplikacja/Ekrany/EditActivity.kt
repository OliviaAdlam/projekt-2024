package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectaplikacja.Models.Recipe
import com.example.projectaplikacja.Viewmodels.FirebaseViewModel
import com.example.projectaplikacja.Viewmodels.FirebaseViewModelFactory
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme
import com.google.firebase.auth.FirebaseAuth

class EditActivity : ComponentActivity() {
    private val viewModel: FirebaseViewModel by viewModels { FirebaseViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeId = intent.getStringExtra("RECIPE_ID") ?: ""
        viewModel.fetchRecipeById(recipeId)
        setContent {
            ProjectAplikacjaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent10(this, viewModel, recipeId)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent10(context: Context, viewModel: FirebaseViewModel, recipeId: String) {
    val singleRecipe by viewModel.singleRecipeState.collectAsState()
    var nazwa by remember { mutableStateOf("") }
    var rodzaj_kuchni by remember { mutableStateOf("") }
    var czas_przygotowania by remember { mutableStateOf("") }
    var wegetariańskie by remember { mutableStateOf(false) }
    var składniki by remember { mutableStateOf("") }
    var sposób_przygotowania by remember { mutableStateOf("") }
    var ulubione by remember { mutableStateOf(false) }

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid ?: ""

    // Update state variables when singleRecipe changes
    LaunchedEffect(singleRecipe) {
        if (singleRecipe != null) {
            nazwa = singleRecipe!!.name
            rodzaj_kuchni = singleRecipe!!.cuisine
            czas_przygotowania = singleRecipe!!.preparationTime.toString()
            wegetariańskie = singleRecipe!!.vegeterian
            składniki = singleRecipe!!.ingredients
            sposób_przygotowania = singleRecipe!!.steps
            ulubione = singleRecipe!!.favourite
        }
    }

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
                        title = { Text(text = "Edytuj produkt") },
                        navigationIcon = {
                            IconButton(onClick = {
                                val intent = Intent(context, MainLoggedInActivity::class.java)
                                context.startActivity(intent)
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Return")
                            }
                        },
                        actions = {}
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(90.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = nazwa,
                                onValueChange = { nazwa = it },
                                label = { Text("Nazwa") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = rodzaj_kuchni,
                                onValueChange = { rodzaj_kuchni = it },
                                label = { Text("Rodzaj kuchni") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = czas_przygotowania,
                                onValueChange = { czas_przygotowania = it },
                                label = { Text("Czas przygotowania w minutach") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Wegetariańskie")
                            Spacer(modifier = Modifier.width(8.dp))
                            Checkbox(
                                checked = wegetariańskie,
                                onCheckedChange = { wegetariańskie = it },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = składniki,
                                onValueChange = { składniki = it },
                                label = { Text("Składniki (format: nazwa1 ilość1, nazwa2 ilość2)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = sposób_przygotowania,
                                onValueChange = { sposób_przygotowania = it },
                                label = { Text("Sposób przygotowania") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Ulubione")
                            Spacer(modifier = Modifier.width(8.dp))
                            Checkbox(
                                checked = ulubione,
                                onCheckedChange = { ulubione = it },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FilledTonalButton(
                                onClick = {
                                    viewModel.updateRecipe(
                                        Recipe(
                                            id = singleRecipe?.id ?: "",
                                            name = nazwa,
                                            cuisine = rodzaj_kuchni,
                                            preparationTime = czas_przygotowania.toInt(),
                                            vegeterian = wegetariańskie,
                                            ingredients = składniki,
                                            steps = sposób_przygotowania,
                                            favourite = ulubione,
                                            userId = userId
                                        )
                                    )
                                    val intent = Intent(context, MainLoggedInActivity::class.java)
                                    context.startActivity(intent)
                                    Toast.makeText(context, "Edytowano pozycję", Toast.LENGTH_SHORT).show()
                                }) {
                                Text("Edytuj")
                            }
                            FilledTonalButton(
                                onClick = {
                                    viewModel.deleteRecipe(recipeId)
                                    Log.d("ID DELETE", recipeId)
                                    val intent = Intent(context, MainLoggedInActivity::class.java)
                                    context.startActivity(intent)
                                    Toast.makeText(context, "Usunięto pozycję", Toast.LENGTH_SHORT).show()
                                }) {
                                Text("Usuń")
                            }
                        }
                    }
                }
            )
        }
    }
}
