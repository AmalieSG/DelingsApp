package com.example.myapplication.ui.screens.productScreens

import com.example.myapplication.viewmodel.UserViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.routes.ScreenRoutes
import com.example.myapplication.viewmodel.Product
import com.example.myapplication.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddProductScreen(userId: String?, navController: NavController, productViewModel: ProductViewModel) {
    var product by remember { mutableStateOf(Product("", "", "", 0.0, mutableListOf(), "", "", mutableListOf())) }
    var updatedName by remember { mutableStateOf(product.name) }
    var updatedOwnerId by remember { mutableStateOf(product.ownerId) }
    var updatedDescription by remember { mutableStateOf(product.description) }
    var updatedPrice by remember { mutableDoubleStateOf(product.price) }
    var updatedPhotos = remember { mutableStateListOf(*product.photos.toTypedArray()) }
    var updatedLocation by remember { mutableStateOf(product.location) }
    var updatedCategory by remember { mutableStateOf(product.category) }

    var photoUrl by remember { mutableStateOf("") }
    updatedOwnerId = userId ?: ""
    val coroutineScope = rememberCoroutineScope()
    System.out.println(userId)
    if (!userId.isNullOrBlank()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = updatedName,
                onValueChange = { updatedName = it },
                label = { Text("Produkt navn") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = updatedDescription,
                onValueChange = { updatedDescription = it },
                label = { Text("Beskrivelse") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = updatedPrice.toString(),
                onValueChange = { updatedPrice = it.toDoubleOrNull() ?: 0.0 },
                label = { Text("Pris") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = updatedLocation,
                onValueChange = { updatedLocation = it },
                label = { Text("Sted") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = updatedCategory,
                onValueChange = { updatedCategory = it },
                label = { Text("Kategori") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Bilder")

            OutlinedTextField(
                value = photoUrl,
                onValueChange = { photoUrl = it },
                label = { Text("Legg til bilde URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = {
                    if (photoUrl.isNotBlank()) {
                        updatedPhotos.add(photoUrl)
                        photoUrl = ""
                    }
                }
            ) {
                Text("Legg til bilde")
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    coroutineScope.launch {
                        product = product.copy(
                            name = updatedName,
                            ownerId = updatedOwnerId,
                            description = updatedDescription,
                            price = updatedPrice,
                            photos = updatedPhotos.toList(),
                            location = updatedLocation,
                            category = updatedCategory,
                        )
                        productViewModel.addProduct(product)
                        println("Product Added: $updatedName")
                        navController.navigate(ScreenRoutes.Home.route)
                    }
                }
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    } else {
        Text("Du må være logget inn for å legge til et produkt.")
    }


}