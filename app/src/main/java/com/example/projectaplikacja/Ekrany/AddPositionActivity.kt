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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ExitToApp
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
import com.example.projectaplikacja.R
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class AddPositionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent5(this)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent5(context: Context) {
    var nazwa by remember { mutableStateOf("") }
    var rodzaj_kuchni by remember { mutableStateOf("") }
    var czas_przygotowania by remember { mutableStateOf("") }
    var wegetariańskie by remember { mutableStateOf(false) }
    var składniki by remember { mutableStateOf("") }
    var sposób_przygotowania by remember { mutableStateOf("") }

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
                        title = { Text(text = "Dodaj produkt") },
                        navigationIcon = {
                            IconButton(onClick = { val intent = Intent(context,MainLoggedInActivity::class.java)
                                context.startActivity(intent) }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Return")
                            }
                        },
                        actions = {
                            // Dodaj akcje tutaj, jeśli potrzebujesz
                        }
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
                                onValueChange = {
                                    rodzaj_kuchni = it
                                },
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
                                onValueChange = {
                                    czas_przygotowania = it
                                },
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
                                onValueChange = {
                                    składniki = it
                                },
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
                                onValueChange = {
                                    sposób_przygotowania = it
                                },
                                label = { Text("Sposób przygotowania w minutach") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FilledTonalButton(
                                onClick ={val intent = Intent(context,MainLoggedInActivity::class.java)
                                    context.startActivity(intent)
                                    Toast.makeText(context,"Dodano pozycję",Toast.LENGTH_SHORT)
                                }) {
                                Text("Dodaj")
                            }
                        }
                    }
                }
            )
        }
    }
}




