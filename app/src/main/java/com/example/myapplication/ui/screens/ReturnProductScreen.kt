/*package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReturnProductPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Retur Produkt",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Ta bilde av produktet
        Button(
            onClick = {
                navController.navigate("checklist")  // Naviger til sjekklisten
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "Take photo")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ta bilde av produktet")
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Chat med selger
        Button(
            onClick = { /* Handle chat action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Chat med selger")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Returner produkt
        Button(
            onClick = { /* Handle return product action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Returner produkt")
        }
    }
}
*/