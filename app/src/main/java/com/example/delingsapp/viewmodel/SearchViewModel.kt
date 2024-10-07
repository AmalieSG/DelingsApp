package com.example.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import com.example.delingsapp.ui.components.Product
import kotlinx.coroutines.flow.Flow

class SearchViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isSearchInitiated = MutableStateFlow(false)
    val isSearchInitiated: StateFlow<Boolean> = _isSearchInitiated

    private val _products = MutableStateFlow(
        listOf(
            Product("Sofa", "John", "Komfortabel sofa", 5000.0, _location = "Oslo", _category = "Furniture"),
            Product("TV", "Anna", "Flatskjerm TV", 3000.0, _location = "Bergen", _category = "Electronics"),
            Product("Bord", "Emma", "Rundt spisebord", 1500.0, _location = "Trondheim", _category = "Furniture")
        )
    )

    val filteredProducts: Flow<List<Product>> = combine(
        _query,
        _products,
        _isSearchInitiated
    ) { query, products, isSearchInitiated ->
        if (isSearchInitiated) {
            if (query.isEmpty()) {
                products
            } else {
                products.filter { product ->
                    product.name.contains(query, ignoreCase = true) ||
                            product.description.contains(query, ignoreCase = true) ||
                            product.category.contains(query, ignoreCase = true) ||
                            product.owner.contains(query, ignoreCase = true)
                }
            }
        } else {
            emptyList()
        }
    }
    
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun initiateSearch() {
        _isSearchInitiated.value = true
    }
}
