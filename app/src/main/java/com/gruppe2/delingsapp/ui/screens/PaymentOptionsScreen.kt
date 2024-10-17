package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun PaymentOptionsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Velg betalingsmetode",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Visa payment option
        Image(
            painter = painterResource(id = R.drawable.visa_card),
            contentDescription = "Visa",
            modifier = Modifier
                .size(150.dp)
                .clickable {
                    navController.navigate("visa_payment")
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Vipps payment option
        Image(
            painter = painterResource(id = R.drawable.vipps_betaling),
            contentDescription = "Vipps",
            modifier = Modifier
                .size(150.dp)
                .clickable {
                    navController.navigate("vipps_payment")
                }
        )
        Button(onClick = {
            navController.popBackStack()  // Gå tilbake til login-skjermen
        }) {
            Text("Go back")
        }
    }
}
