package com.example.myapplication.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReturnChecklistPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Sjekkliste",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sjekkliste spørsmål
        Text(text = "• Er produktet rengjort eller er fri for synlig smuss?")
        Text(text = "• Er alle deler av produktet inkludert i retur?")
        Text(text = "• Er produktet i samme tilstand som ved utlån?")

        Spacer(modifier = Modifier.height(32.dp))

        // Bilde av produktet
        Box(
            modifier = Modifier
                .size(160.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "+\nTa bilde", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bruk dette bildet knappen
        Button(onClick = {
            // Handle use this image action
        }) {
            Text("Bruk dette bildet")
        }
    }
}
