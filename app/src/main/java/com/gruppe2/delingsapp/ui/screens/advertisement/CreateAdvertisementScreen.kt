package com.gruppe2.delingsapp.ui.screens.advertisement

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.gruppe2.delingsapp.viewmodel.AdvertisementViewModel

@Composable
fun CreateAdvertisementScreen(
    viewModel: AdvertisementViewModel, // Provided by Hilt/DI or manually injected
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf("") }
    val selectedProducts = remember { mutableStateOf(emptyList<Product>()) }
    val availability = remember { mutableStateOf<Pair<String, String>?>(null) }

    Column {
        // Form inputs (text fields, dropdowns, etc.)
        TextField(value = title.value, onValueChange = { title.value = it }, label = { Text("Title") })
        TextField(value = description.value, onValueChange = { description.value = it }, label = { Text("Description") })
        TextField(value = location.value, onValueChange = { location.value = it }, label = { Text("Location") })

        // Submit Button
        Button(onClick = {
            viewModel.createAdvertisement(
                title = title.value,
                description = description.value,
                location = location.value,
                category = selectedCategory.value,
                selectedProducts = selectedProducts.value,
                availability = availability.value,
                onSuccess = { onSuccess() }, // Navigate on success
                onFailure = { onFailure("Failed to create advertisement") } // Handle error
            )
        }) {
            Text("Create Advertisement")
        }
    }
}
