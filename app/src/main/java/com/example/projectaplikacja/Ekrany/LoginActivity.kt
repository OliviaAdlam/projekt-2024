package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent3(this)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent3(context: Context) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    val barColor = if (isEmailValid) Color.Green else Color.Red
    val PassbarColor = if (isPasswordValid) Color.Green else Color.Red

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
                        title = { Text(text = "Logowanie") },
                        navigationIcon = {
                            IconButton(onClick = { val intent = Intent(context,MainActivity::class.java)
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
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .width(4.dp)
                                    .background(color = barColor)
                            )
                            TextField(
                                value = email,
                                onValueChange = { email = it
                                    isEmailValid = it.contains("@")},
                                label = { Text("Email") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .width(4.dp)
                                    .background(color = PassbarColor)
                            )
                            TextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                    isPasswordValid = checkPasswordValidity(it)
                                },
                                label = { Text("Hasło") },
                                visualTransformation = PasswordVisualTransformation(), //zakrycie hasła przy pisaniu
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            FilledTonalButton(
                                onClick = {
                                    if (email.equals("olivia@adlam.com") && password.equals("Olivia123")){
                                        val intent = Intent(context,MainLoggedInActivity::class.java)
                                        context.startActivity(intent)
                                        Toast.makeText(context,"Zalogowano", Toast.LENGTH_SHORT)
                                    }
                                }) {
                                Text("Zaloguj")
                            }
                        }
                        Column {
                            TextButton (
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { val intent = Intent(context,RegisterActivity::class.java)
                                    context.startActivity(intent) }
                            ) {
                                Text("Stwórz konto")
                            }
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Akcja dla zapomnianego hasła */ }
                            ) {
                                Text("Zapomniałeś hasło?")
                            }
                        }
                        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Zaloguj się za pomocą google")
                        Spacer(modifier = Modifier.height(16.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Rounded.ExitToApp,
                                contentDescription = "Google"
                            )
                        }
                    }
                }
            )
        }
    }
}
fun checkPasswordValidity(password: String): Boolean {
    if (password.length < 8) return false
    var containsUpperCase = false
    var containsLowerCase = false
    var containsDigit = false

    for (char in password) {
        when {
            char.isUpperCase() -> containsUpperCase = true
            char.isLowerCase() -> containsLowerCase = true
            char.isDigit() -> containsDigit = true
        }
    }

    return containsUpperCase && containsLowerCase && containsDigit
}



