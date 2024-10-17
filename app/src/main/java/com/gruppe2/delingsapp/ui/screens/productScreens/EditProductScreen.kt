package com.gruppe2.delingsapp.ui.screens.productScreens

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
import com.gruppe2.delingsapp.viewmodel.Product
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
// Lagt til midlertidig for å omgå error
//import com.gruppe2.delingsapp.ui.components.Product

@Composable
fun EditProductScreen(productName: String?, navController: NavController, productViewModel: ProductViewModel) {
    var product by remember { mutableStateOf(Product("", "", "", 0.0, mutableListOf(), "", "", false)) }
    var updatedName by remember { mutableStateOf(product.name) }
    var updatedOwner by remember { mutableStateOf(product.owner) }
    var updatedDescription by remember { mutableStateOf(product.description) }
    var updatedPrice by remember { mutableDoubleStateOf(product.price) }
    var updatedPhotos = remember { mutableStateListOf(*product.photos.toTypedArray()) }
    var updatedLocation by remember { mutableStateOf(product.location) }
    var updatedCategory by remember { mutableStateOf(product.category) }
    var updatedStatus by remember { mutableStateOf(product.status) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productName) {
        if (productName != null) {
            val result = productViewModel.getProduct(productName)
            if (result != null) {
                product = result
                updatedName = result.name
                updatedOwner = result.owner
                updatedDescription = result.description
                updatedPrice = result.price
                updatedPhotos.clear()
                updatedPhotos.addAll(result.photos)
                updatedLocation = result.location
                updatedCategory = result.category
                updatedStatus = result.status
            } else {
                println("Kunne ikke finne produkt")
            }
        }
    }
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
            value = updatedOwner,
            onValueChange = { updatedOwner = it },
            label = { Text("Owner") },
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
                        owner = updatedOwner,
                        description = updatedDescription,
                        price = updatedPrice,
                        photos = updatedPhotos.toList(),
                        location = updatedLocation,
                        category = updatedCategory,
                        status = updatedStatus
                    )
                    productViewModel.updateProduct(product)
                    println("Product updated: $updatedName")
                }
            }
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }


}