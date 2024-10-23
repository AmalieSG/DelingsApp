package com.gruppe2.delingsapp.data.repository

sealed class ProductCategories(val categoryName: String) {
    object Electronics : ProductCategories("Elektronikk")
    object Clothing : ProductCategories("Klær")
    object Tools : ProductCategories("Verktøy")

}