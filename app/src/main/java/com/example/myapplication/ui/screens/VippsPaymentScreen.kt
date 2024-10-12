package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VippsPaymentScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    var paymentState by remember { mutableStateOf("idle") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(paymentState) {
        if (paymentState == "processing") {
            delay(3000) // Vent i 3 sekunder
            paymentState = "success"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (paymentState) {
            "idle" -> {
                Text(text = "Betal med Vipps", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Telefonnummer") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        paymentState = "processing"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Betal nå")
                }
            }
            "processing" -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Behandler betaling...", fontSize = 18.sp)
            }
            "success" -> {
                Text(
                    text = "Betalingen var vellykket!",
                    fontSize = 24.sp,
                    color = Color.Green
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        navController.popBackStack() // Går ett steg tilbake
                        navController.popBackStack() // Går ett steg tilbake igjen
                    }
                }) {
                    Text("Go back")
                }
            }
        }
    }
}
