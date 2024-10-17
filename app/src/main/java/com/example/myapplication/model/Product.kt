package com.example.myapplication.model

data class Product(
    val name: String = "",
    val owner: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val photos: List<String> = mutableListOf(),
    val location: String = "",
    val category: String = "",
    val status: Boolean = false
)