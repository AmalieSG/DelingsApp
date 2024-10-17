package com.example.myapplication.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductEvaluationPage() {
    var friendlinessRating by remember { mutableStateOf(0) }
    var responseTimeRating by remember { mutableStateOf(0) }
    var qualityRating by remember { mutableStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Produkt 1",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "1 dag siden", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Vurdering av vennlighet
        Text(text = "Vennlighet", fontSize = 18.sp)
        StarRating(rating = friendlinessRating) { rating -> friendlinessRating = rating }

        // Vurdering av responstid
        Text(text = "Responsstid", fontSize = 18.sp)
        StarRating(rating = responseTimeRating) { rating -> responseTimeRating = rating }

        // Vurdering av kvalitet
        Text(text = "Kvalitet", fontSize = 18.sp)
        StarRating(rating = qualityRating) { rating -> qualityRating = rating }

        Spacer(modifier = Modifier.height(16.dp))

        // Spørsmål om tilfredshet
        Text(text = "Hvor tilfredsstillende var produktet?", fontSize = 18.sp)

        // Star rating for product satisfaction
        StarRating(rating = 0) { /* Handle overall satisfaction rating here */ }

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback text area
        Text(text = "Tilbakemelding", fontSize = 18.sp)
        BasicTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(onClick = {
            // Handle the submission of the ratings and feedback here
        }) {
            Text("Send vurdering")
        }
    }
}

@Composable
fun StarRating(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = if (i <= rating) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}
