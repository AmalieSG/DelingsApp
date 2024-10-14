package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productViewModel: ProductViewModel
) : ViewModel() {

    // Eksempel på produkter
    val _products = listOf(
        Product(
            name = "Mountain Bike",
            owner = "John Doe",
            description = "A sturdy mountain bike suitable for rough terrains.",
            price = 150.0,
            photos = listOf("https://example.com/bike1.jpg", "https://example.com/bike2.jpg"),
            location = "Oslo",
            category = "Sports & Outdoors",
            status = true
        ),
        Product(
            name = "Tent",
            owner = "Jane Smith",
            description = "A spacious 4-person tent, ideal for camping trips.",
            price = 100.0,
            photos = listOf("https://example.com/tent1.jpg"),
            location = "Bergen",
            category = "Camping & Hiking",
            status = true
        ),
        Product(
            name = "Electric Drill",
            owner = "Mark Johnson",
            description = "Powerful cordless electric drill for home improvements.",
            price = 75.0,
            photos = listOf("https://example.com/drill1.jpg", "https://example.com/drill2.jpg"),
            location = "Trondheim",
            category = "Tools",
            status = false
        ),
        Product(
            name = "Kayak",
            owner = "Emily Davis",
            description = "Single-person kayak, perfect for lake adventures.",
            price = 200.0,
            photos = listOf("https://example.com/kayak1.jpg"),
            location = "Stavanger",
            category = "Water Sports",
            status = true
        )
    )


    // Tilstand for søkespørringen (brukes under skriving)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Brukes for å trigge søk når brukeren trykker på søkeknappen eller "Enter"
    private val _triggerSearch = MutableSharedFlow<Unit>()

    // Filtrerte produkter basert på søkespørringen og kun når søket er trigget
    // Databasesøk ->
    /*val filteredProducts: StateFlow<List<Product>> = combine(
        productViewModel.products,  // Produktene fra Firebase
        _triggerSearch              // Trigger søket
    ) { products, _ ->
        val query = _searchQuery.value
        if (query.isBlank()) {
            emptyList()
        } else {
            products.filter { product ->
                // Utfører søk på tvers av felter
                product.name.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true) ||
                        product.location.contains(query, ignoreCase = true) ||
                        product.owner.contains(query, ignoreCase = true)
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )*/

    // Eksempelliste søk ->
    val filteredProducts: StateFlow<List<Product>> = _triggerSearch
        .flatMapLatest {
            val query = _searchQuery.value
            flowOf(
                if (query.isBlank()) {
                    emptyList()
                } else {
                    _products.filter { product ->
                        // Utfører søk på tvers av alle relevante felter (navn, beskrivelse, kategori, plassering, eier)
                        product.name.contains(query, ignoreCase = true) ||
                                product.description.contains(query, ignoreCase = true) ||
                                product.category.contains(query, ignoreCase = true) ||
                                product.location.contains(query, ignoreCase = true) ||
                                product.owner.contains(query, ignoreCase = true)
                    }
                }
            )
        }
        .stateIn(
            scope = viewModelScope,           // scope for å holde tilstanden levende
            started = SharingStarted.Lazily,  // Start strømmen når det er minst én observatør
            initialValue = emptyList()        // Startverdi før søket er trigget
        )

    // Oppdater søkespørringen når brukeren skriver inn i søkefeltet
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    // Trigger søk når brukeren trykker på søkeknappen eller "Enter"
    fun triggerSearch() {
        viewModelScope.launch {
            _triggerSearch.emit(Unit)  // Emit signalet for å trigge søket
        }
    }
}
