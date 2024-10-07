package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.layout.*  // Importer nødvendige Composables
import androidx.compose.material3.*  // Importer Material3 for UI-komponenter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.UserViewModel


@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Inndatafelt for e-post
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Inndatafelt for passord
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Viser feilmelding hvis noe går galt
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        // Login-knapp
        Button(
            onClick = {
                // Kall login-metoden i UserViewModel, som bruker Firebase Authentication
                userViewModel.login(email, password) { success: Boolean, error: String? ->
                    if (success) {
                        // Naviger til startsiden etter vellykket innlogging
                        navController.navigate("home")
                    } else {
                        // Viser feilmelding hvis innloggingen mislyktes
                        errorMessage = error ?: "Unknown error occurred"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Registreringsknapp
        Button(
            onClick = { navController.navigate("register") },  // Naviger til registreringsskjermen
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}
