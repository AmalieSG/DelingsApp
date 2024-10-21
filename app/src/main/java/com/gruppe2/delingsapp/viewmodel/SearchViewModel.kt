package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productViewModel: ProductViewModel
) : ViewModel() {

    // Eksempel på produkter
    /*val _products = listOf(
        Product(
            name = "Mountain Bike",
            ownerId = "John Doe",
            description = "A sturdy mountain bike suitable for rough terrains.",
            price = 150.0,
            photos = listOf("https://example.com/bike1.jpg", "https://example.com/bike2.jpg"),
            location = "Oslo",
            category = "Sports & Outdoors",
        ),
        Product(
            name = "Tent",
            ownerId = "Jane Smith",
            description = "A spacious 4-person tent, ideal for camping trips.",
            price = 100.0,
            photos = listOf("https://example.com/tent1.jpg"),
            location = "Bergen",
            category = "Camping & Hiking",
        ),
        Product(
            name = "Electric Drill",
            ownerId = "Mark Johnson",
            description = "Powerful cordless electric drill for home improvements.",
            price = 75.0,
            photos = listOf("https://example.com/drill1.jpg", "https://example.com/drill2.jpg"),
            location = "Trondheim",
            category = "Tools",
        ),
        Product(
            name = "Kayak",
            ownerId = "Emily Davis",
            description = "Single-person kayak, perfect for lake adventures.",
            price = 200.0,
            photos = listOf("https://example.com/kayak1.jpg"),
            location = "Stavanger",
            category = "Water Sports",
        )
    )*/

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _triggerSearch = MutableSharedFlow<Unit>()

    // Databasesøk ->
    val filteredProducts: StateFlow<List<Product>> = combine(
        productViewModel.products,
        _triggerSearch
    ) { products, _ ->
        val query = _searchQuery.value
        if (query.isBlank()) {
            emptyList()
        } else {
            products.filter { product ->
                product.name.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true) ||
                        product.location.contains(query, ignoreCase = true)
                        //product.owner.contains(query, ignoreCase = true)
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    // Eksempelliste søk ->
    /*val filteredProducts: StateFlow<List<Product>> = _triggerSearch
        .flatMapLatest {
            val query = _searchQuery.value
            flowOf(
                if (query.isBlank()) {
                    emptyList()
                } else {
                    _products.filter { product ->
                        product.name.contains(query, ignoreCase = true) ||
                                product.description.contains(query, ignoreCase = true) ||
                                product.category.contains(query, ignoreCase = true) ||
                                product.location.contains(query, ignoreCase = true)
                                // TODO: fikse denne linjen product.ownerId.contains(query, ignoreCase = true)
                    }
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )*/

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    fun triggerSearch() {
        viewModelScope.launch {
            _triggerSearch.emit(Unit)
        }
    }
}
