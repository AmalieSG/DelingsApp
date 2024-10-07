package com.gruppe2.delingsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*  // Pass på at dette er importert
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.UserViewModel

@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel) {
    val context = LocalContext.current

    // States for brukerinput
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)
        }

        // Knappeklikk for å registrere brukeren
        Button(onClick = {
            // Kall til register-metoden
            userViewModel.register(username, password) { success, error ->
                if (success) {
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("login?message=Registration successful")
                } else {
                    errorMessage = error ?: "Registration failed"
                }
            }
        }) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Go Back-knapp som navigerer tilbake til LoginScreen
        Button(onClick = {
            navController.popBackStack()  // Naviger tilbake til forrige skjerm
        }) {
            Text("Go Back")
        }
    }
}
