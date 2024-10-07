package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.gruppe2.delingsapp.ui.components.Product

class ProductViewModel : ViewModel() {
    private val products = mutableStateListOf<Product>()

    fun addProduct(product: Product) {
        products.add(product)
    }
    fun removeProduct(product: Product) {
        products.remove(product)
    }
    fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.name == product.name }
        if (index != -1) {
            products[index] = product
        }
    }

}