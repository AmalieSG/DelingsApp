package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(username: String?, userViewModel: UserViewModel, navController: NavController) {
    var user by remember { mutableStateOf<com.gruppe2.delingsapp.viewmodel.User?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(username) {
        if (username != null) {
            coroutineScope.launch {
                val result = userViewModel.getUserByUsername(username)
                if (result != null) {
                    user = result
                } else {
                    errorMessage = "Bruker ikke funnet"
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user != null) {
            // Profilseksjon med innebygd ikon for brukerprofil
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Navn og rating
            Text(
                text = user!!.username,  // Brukerens navn
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating-seksjon med innebygd stjerneikon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Rating", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,  // Stjerneikon
                    contentDescription = "Rating",
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ProfileButton(text = "Rediger profil", onClick = { /* Handle profile edit */ })
            Spacer(modifier = Modifier.height(16.dp))
            ProfileButton(text = "Mine annonser", onClick = { /* Handle ads */ })
            Spacer(modifier = Modifier.height(16.dp))
            ProfileButton(text = "Definere responstid", onClick = { /* Handle response time */ })
            Spacer(modifier = Modifier.height(16.dp))
            ProfileButton(text = "Sette tidsrom for tilgjengelighet", onClick = { /* Handle availability */ })

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Forside", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logg ut-knappen
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logg ut", color = MaterialTheme.colorScheme.onError)
            }
        } else if (errorMessage.isNotEmpty()) {
            // Hvis brukeren ikke finnes, vis feilmelding
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun ProfileButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFFB8860B),
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Color(0xFFB8860B))
    ) {
        Text(text)
    }
}
