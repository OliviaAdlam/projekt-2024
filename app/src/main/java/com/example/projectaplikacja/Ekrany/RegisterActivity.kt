package com.example.projectaplikacja.Ekrany

import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.projectaplikacja.Models.AuthManager
import com.example.projectaplikacja.R
import com.example.projectaplikacja.ui.theme.ProjectAplikacjaTheme

class RegisterActivity : ComponentActivity() {
    private val authManager = AuthManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAplikacjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent4(this, authManager)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent4(context: Context, authManager: AuthManager) {
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPassword1Valid by remember { mutableStateOf(false) }
    var isPassword2Valid by remember { mutableStateOf(false) }
    val barColor = if (isEmailValid) Color.Green else Color.Red
    val Pass1barColor = if (isPassword1Valid) Color.Green else Color.Red
    val Pass2barColor = if (isPassword2Valid) Color.Green else Color.Red

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
                        title = { Text(text = "Rejestracja") },
                        navigationIcon = {
                            IconButton(onClick = { val intent = Intent(context,LoginActivity::class.java)
                                context.startActivity(intent)
                                if (context is RegisterActivity) {
                                    context.finish()
                                }}) {
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
                                    .background(color = Pass1barColor)
                            )
                            TextField(
                                value = password1,
                                onValueChange = {
                                    password1 = it
                                    isPassword1Valid = checkPasswordValidity(it)
                                },
                                label = { Text("Hasło") },
                                visualTransformation = PasswordVisualTransformation(), //zakrycie hasła przy pisaniu
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
                                    .background(color = Pass2barColor)
                            )
                            TextField(
                                value = password2,
                                onValueChange = {
                                    password2 = it
                                    isPassword2Valid = checkPasswordsValidity(it,password1)
                                },
                                label = { Text("Powtórz hasło") },
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
                                onClick ={
                                    if (isEmailValid == true && isPassword1Valid == true && isPassword2Valid == true){
                                        authManager.signUpWithEmailPassword(email,password1)
                                            .addOnSuccessListener {
                                                val intent = Intent(context,LoginActivity::class.java)
                                                context.startActivity(intent)
                                                if (context is RegisterActivity) {
                                                    context.finish()
                                                }
                                                Toast.makeText(context,"Zarejestrowano", Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnFailureListener{e ->
                                                Toast.makeText(context,"Error ${e.message}", Toast.LENGTH_SHORT).show()

                                            }
                                    }
                                    else if (isEmailValid==false){
                                        Toast.makeText(context,"Podaj poprawny email",Toast.LENGTH_SHORT)
                                    }
                                    else if (isPassword1Valid==false){
                                        Toast.makeText(context,"Hasło powinno mieć 8 znaków. Conajmniej 1 dużą literę, 1 małą i 1 cyfrę",Toast.LENGTH_LONG)
                                    }
                                    else if (isPassword2Valid==false){
                                        Toast.makeText(context,"Hasła muszą być takie same",Toast.LENGTH_SHORT)
                                    }
                                }) {
                                Text("Zarejestruj")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black, thickness = 2.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            )
        }
    }
}
fun checkPasswordsValidity(password1: String,password2: String): Boolean {
    if (password1.equals(password2)){ return true}
    else {return false}
}



