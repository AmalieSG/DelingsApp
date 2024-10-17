package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomerSupportPage() {
    var caseNumber by remember { mutableStateOf("") }
    val contactInfo = "+47 123 123 12"
    val emailInfo = "Delingsapp@hiof.no"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hvordan kan vi hjelpe deg",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Saksnummer input
        BasicTextField(
            value = caseNumber,
            onValueChange = { caseNumber = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.Gray)
                .padding(16.dp),
            singleLine = true
        )

        // Produkt knapp
        SupportButton(text = "Produkt") {
            // Logikk for navigering til produkt
        }

        // FAQ knapp
        SupportButton(text = "FAQ") {
            // Logikk for navigering til FAQ
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Kontaktinfo", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // Kontaktinfo for telefon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Phone, contentDescription = "Phone Icon")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = contactInfo, fontSize = 16.sp)
        }

        // Kontaktinfo for e-post
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Email, contentDescription = "Email Icon")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = emailInfo, fontSize = 16.sp)
        }
    }
}

@Composable
fun SupportButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(text)
    }
}
