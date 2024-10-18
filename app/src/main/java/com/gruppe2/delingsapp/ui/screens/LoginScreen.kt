package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.border
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
import java.util.regex.Pattern


@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var isEmailMissing by remember { mutableStateOf(false) }
    var isPasswordMissing by remember { mutableStateOf(false) }

    // Email validation pattern (simple version)
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // E-post input
        TextField(
            value = email,
            onValueChange = { email = it; isEmailMissing = false },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isEmailMissing) 2.dp else 0.dp,
                    color = if (isEmailMissing) Color.Red else Color.Transparent
                )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Passord input
        TextField(
            value = password,
            onValueChange = { password = it; isPasswordMissing = false },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isPasswordMissing) 2.dp else 0.dp,
                    color = if (isPasswordMissing) Color.Red else Color.Transparent
                )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Viser feilmelding hvis noe går galt
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        // Login-knapp
        Button(
            onClick = {
                // Sjekk for tomme felter
                isEmailMissing = email.isBlank()
                isPasswordMissing = password.isBlank()

                if (isEmailMissing || isPasswordMissing) {
                    errorMessage = "Missing fields"
                } else if (!emailPattern.matcher(email).matches()) {
                    errorMessage = "Email is badly formatted"
                } else {
                    // Hvis e-postformatet er riktig, prøv å logge inn
                    userViewModel.login(email, password) { success, error ->
                        if (success) {
                            userViewModel.getCurrentUser { user ->
                                if (user != null) {
                                    // Navigate to profile with username or user ID
                                    navController.navigate("profile/${user.username}")
                                } else {
                                    errorMessage = "User data not found after login."
                                }
                            }
                        } else {
                            errorMessage = error ?: "Login failed"
                        }
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

