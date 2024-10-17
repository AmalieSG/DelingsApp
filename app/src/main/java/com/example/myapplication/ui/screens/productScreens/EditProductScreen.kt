package com.example.myapplication.ui.screens.productScreens

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
import com.example.myapplication.viewmodel.Product
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditProductScreen(productName: String?, navController: NavController, productViewModel: ProductViewModel, userViewModel: UserViewModel) {
    var product by remember { mutableStateOf(Product("", "", "", 0.0, mutableListOf(), "", "", mutableListOf())) }
    var updatedName by remember { mutableStateOf(product.name) }
    var updatedOwner by remember { mutableStateOf(product.ownerId) }
    var updatedDescription by remember { mutableStateOf(product.description) }
    var updatedPrice by remember { mutableDoubleStateOf(product.price) }
    var updatedPhotos = remember { mutableStateListOf(*product.photos.toTypedArray()) }
    var updatedLocation by remember { mutableStateOf(product.location) }
    var updatedCategory by remember { mutableStateOf(product.category) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productName) {
        if (productName != null) {
            val result = productViewModel.getProduct(productName)
            if (result != null) {
                product = result
                updatedName = result.name
                updatedOwner = result.ownerId
                updatedDescription = result.description
                updatedPrice = result.price
                updatedPhotos.clear()
                updatedPhotos.addAll(result.photos)
                updatedLocation = result.location
                updatedCategory = result.category
            } else {
                println("Kunne ikke finne produkt")
            }
        }
    }
    updatedOwner = userViewModel.currentUserId
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
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = updatedDescription,
            onValueChange = { updatedDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = updatedPrice.toString(),
            onValueChange = { updatedPrice = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
                    )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = updatedLocation,
            onValueChange = { updatedLocation = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
       

        Button(
            onClick = {
                coroutineScope.launch {
                    product = product.copy(
                        name = updatedName,
                        ownerId = updatedOwner,
                        description = updatedDescription,
                        price = updatedPrice,
                        photos = updatedPhotos.toList(),
                        location = updatedLocation,
                        category = updatedCategory,
                    )
                    productViewModel.updateProduct(product)
                    println("Product updated: $updatedName")
                    navController.popBackStack()
                }
            }
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }


}