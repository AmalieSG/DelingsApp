package com.gruppe2.delingsapp.ui.screens.advertisement


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gruppe2.delingsapp.data.model.Product
import com.gruppe2.delingsapp.viewmodel.sampleProducts

@Composable
fun AddProductScreen() {
    var productName by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var price by remember { mutableStateOf(TextFieldValue()) }
    var deposit by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add a Product", style = MaterialTheme.typography.titleLarge)

        // Input fields for product details
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price per Day") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = deposit,
            onValueChange = { deposit = it },
            label = { Text("Deposit") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Button to add the product
        Button(
            onClick = {
                val newProduct = Product(
                    id = sampleProducts.size + 1, // Auto-generate ID
                    owner = "User", // Replace with actual user data in real app
                    name = productName.text,
                    description = description.text,
                    availability = true,
                    price = price.text.toDoubleOrNull() ?: 0.0,
                    deposit = deposit.text.toDoubleOrNull() ?: 0.0
                )
                sampleProducts.add(newProduct) // Add product to sample list
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Product")
        }
    }
}
