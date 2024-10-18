package com.gruppe2.delingsapp.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

// Represents a single product that a user wants to rent out
data class Product(
    val id: Int, // Unique identifier for each product
    val owner: String, // The owner of the product
    val name: String, // Name of the product
    val description: String, // Description of the product
    val availability: Boolean, // Whether the product is available for rent
    val price: Double, // Rental price per day
    val deposit: Double, // Security deposit amount
    val img: String? = null // Image URL or path for the product
)