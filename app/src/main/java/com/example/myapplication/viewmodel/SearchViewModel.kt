package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import com.example.myapplication.model.Product
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    // Eksempel på produkter
    private val _products = listOf(
        Product(
            _name = "Item 1",
            _owner = "Owner 1",
            _description = "Description of product 1",
            _price = 100.0,
            _location = "Oslo",
            _category = "Electronics"
        ),
        Product(
            _name = "Item 2",
            _owner = "Owner 2",
            _description = "Description of product 2",
            _price = 200.0,
            _location = "Bergen",
            _category = "Home Appliances"
        ),
        Product(
            _name = "Product 3",
            _owner = "Owner 3",
            _description = "Another description",
            _price = 300.0,
            _location = "Trondheim",
            _category = "Books"
        )
    )

    // Tilstand for søkespørringen (brukes under skriving)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Brukes for å trigge søk når brukeren trykker på søkeknappen eller "Enter"
    private val _triggerSearch = MutableSharedFlow<Unit>()

    // Filtrerte produkter basert på søkespørringen og kun når søket er trigget
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
