package com.gruppe2.delingsapp.viewmodel

import androidx.compose.runtime.*
import com.gruppe2.delingsapp.data.model.Advertisement
import com.gruppe2.delingsapp.data.model.Product

// Initial sample data for products
val sampleProducts = mutableStateListOf(
    Product(1, "Alice", "Lawn Mower", "A powerful lawn mower for gardening.", true, 15.0, 50.0, null),
    Product(2, "Bob", "Drill", "Electric drill with multiple speed settings.", true, 10.0, 30.0, null),
    Product(3, "Alice", "Ladder", "7-foot aluminum ladder.", true, 8.0, 25.0, null)
)

// List to hold advertisements
val advertisements = mutableStateListOf<Advertisement>()

// Function to create an advertisement
fun createAdvertisement(
    owner: String,
    name: String,
    description: String,
    location: String,
    category: String,
    selectedProducts: List<Product>
) {
    val newAd = Advertisement(
        id = advertisements.size + 1, // Auto-generate ID
        owner = owner,
        name = name,
        description = description,
        location = location,
        category = category,
        products = selectedProducts
    )
    advertisements.add(newAd) // Add the new advertisement to the list
}
