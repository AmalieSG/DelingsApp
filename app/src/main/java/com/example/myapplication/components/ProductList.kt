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
import com.example.myapplication.viewmodel.Product

@Composable
fun ProductList(products: List<Product>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            if (products.isEmpty()) {
                item {
                    Text(
                        text = "No products found",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            } else {
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}
