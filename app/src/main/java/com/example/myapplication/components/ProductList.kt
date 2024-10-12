package com.example.myapplication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Product

@Composable
fun ProductList(products: List<Product>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth() // Fyller hele bredden
            .padding(16.dp), // Flytter padding her i stedet
        color = MaterialTheme.colorScheme.surfaceVariant // Bruker colorScheme fra Material 3
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp) // Holder padding for LazyColumn
        ) {
            if (products.isEmpty()) {
                item {
                    Text(text = "No products found", style = MaterialTheme.typography.headlineMedium) // Typografi for Material 3
                }
            } else {
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}
