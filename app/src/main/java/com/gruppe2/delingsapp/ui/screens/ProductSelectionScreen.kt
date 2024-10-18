package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gruppe2.delingsapp.viewmodel.AdvertisementViewModel


// Screen som lar User velge om products til en advertisement
/*
@Composable
fun ProductSelectionScreen(viewModel: AdvertisementViewModel) {
    val myProducts = viewModel.getMyProducts() // Fetch user's products

    Column {
        Text(text = "Select Products (Optional)")
        // Sjekker først at listen "myProducts" ikke er tom.
        if (myProducts.isNotEmpty()) {
            LazyColumn {
                items(myProducts) { product ->
                    Row {
                        Checkbox(
                            checked = viewModel.selectedProducts.contains(product),
                            onCheckedChange = {
                                if (it) viewModel.selectedProducts.add(product)
                                else viewModel.selectedProducts.remove(product)
                            }
                        )
                        Text(text = product.name)
                    }
                }
            }
        } else {
            Text(text = "You have no products available")
        }
    }
}
*/

@Composable
fun ProductSelectionScreen(viewModel: AdvertisementViewModel) {
    val allProducts = getUserProducts() // Function to fetch user's "My Products"
    val selectedProducts = viewModel.selectedProducts

    Column {
        Text("Select Products (optional)")

        LazyColumn {
            items(allProducts) { product ->
                val isSelected = selectedProducts.contains(product)
                ProductItem(product, isSelected) { isChecked ->
                    if (isChecked) {
                        viewModel.addProduct(product)
                    } else {
                        viewModel.removeProduct(product)
                    }
                }
            }
        }

        Button(
            onClick = { /* Navigate to next step, with selected products or empty list */ },
            enabled = true // Continue even if no products are selected
        ) {
            Text("Continue")
        }
    }
}

fun getUserProducts(): Int {

}

@Composable
fun ProductItem(product: Product, isSelected: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row {
        Text(product.name)
        Checkbox(
            checked = isSelected,
            onCheckedChange = onCheckedChange
        )
    }
}



