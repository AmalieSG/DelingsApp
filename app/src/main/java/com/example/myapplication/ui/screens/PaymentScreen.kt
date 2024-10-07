package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun PaymentScreen() {
    // States for input fields
    var cardHolderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text(
            text = "Velg betalingsmetode",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card Type (e.g., VISA)
        Image(
            painter = painterResource(id = R.drawable.visa_card),
            contentDescription = "Visa",
            modifier = Modifier.size(100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.vipps_betaling),
            contentDescription = "Vipps",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cardholder's Name Input
        TextField(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            label = { Text("Kortholders navn") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Card Number Input
        TextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Kortnummer") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Expiry Date and CVV Input
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("DD/MM/YY") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Button
        Button(
            onClick = { /* Handle payment logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Betal med Vipps", color = Color.White)
        }
    }
}
