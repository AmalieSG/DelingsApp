package com.gruppe2.delingsapp.ui.screens.productScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.Product
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.rememberCoroutineScope
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReserveProductScreen(productName: String?, navController: NavController, productViewModel: ProductViewModel, userViewModel: UserViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var product by remember { mutableStateOf(Product("", "", "", 0.0, mutableListOf(), "", "", mutableListOf())) }
    var startDateTime by remember { mutableStateOf("") }
    var endDateTime by remember { mutableStateOf("") }
    var renterId by remember { mutableStateOf("") }

    LaunchedEffect(productName) {
        if (productName != null) {
            val result = productViewModel.getProduct(productName)
            if (result != null) {
                product = result
            } else {
                println("Kunne ikke finne produkt")
            }
        }
    }

    renterId = userViewModel.currentUserId.toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = startDateTime,
            onValueChange = { startDateTime = it },
            label = { Text("Start dato og tid: (yyyy-MM-dd HH:mm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = endDateTime,
            onValueChange = { endDateTime = it },
            label = { Text("avslutnings dato: (yyyy-MM-dd HH:mm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                coroutineScope.launch {
                    val start = startDateTime
                    val end = endDateTime
                    if (productName != null) {
                        productViewModel.reserveProduct(productName, renterId, start, end)
                    }
                }
            }
        ) {
            Text("Reserver produkt")
        }


        Spacer(modifier = Modifier.height(16.dp))


        product?.reservations?.forEach { reservation ->
            Text(text = "Reservasjon av ${reservation.renterId} fra ${reservation.startDateTime} til ${reservation.endDateTime}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val start = startDateTime
                    val end = endDateTime
                    if (productName != null) {
                        productViewModel.removeReservation(productName, renterId, start, end)
                    }
                }
            }
        ) {
            Text("Fjern reservasjon")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()){
            Text("Tilbake")
        }

    }




}