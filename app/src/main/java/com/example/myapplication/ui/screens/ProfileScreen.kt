package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.UserViewModel

@Composable
fun ProfileScreen(username: String?, userViewModel: UserViewModel) {
    // Prøv å hente brukeren fra UserViewModel
    val user = userViewModel.getUserByUsername(username)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user != null) {
            // Vis brukerens profil
            Text(text = "Velkommen ${user.username}")
            Spacer(modifier = Modifier.height(16.dp))
            // Her kan du legge til flere elementer for å vise brukerens profilinformasjon
            // Basert på bildet du lastet opp, kan du legge til knapper for "Rediger profil", "Mine annonser", etc.
            Button(onClick = { /* Rediger profil funksjon */ }) {
                Text("Rediger profil")
            }
        } else {
            // Hvis brukeren ikke ble funnet, vis feilmelding
            Text(text = "Bruker ikke funnet", color = MaterialTheme.colorScheme.error)
        }
    }
}
