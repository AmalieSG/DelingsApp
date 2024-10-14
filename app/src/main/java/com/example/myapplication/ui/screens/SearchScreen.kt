package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.SearchBar
import com.example.myapplication.components.ProductList
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    productViewModel: ProductViewModel
) {

    // Oppretter en instans av SearchViewModel manuelt, med produktViewModel som parameter
    val searchViewModel = remember { SearchViewModel(productViewModel) }

    // Henter produktene når skjermen vises
    LaunchedEffect(Unit) {
        productViewModel.fetchAllProducts()
    }
    // Husk BottomSheetState
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded =  false)
    )

    val coroutineScope = rememberCoroutineScope()

    val searchQuery by searchViewModel.searchQuery.collectAsState()
    //val filteredProducts by searchViewModel.filteredProducts.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            // Filtrerte produkter i bottom sheet
            ProductList(products = filteredProducts)
        },
        sheetPeekHeight = 100.dp, // Justerer start høyden på bottom sheet
        sheetShape = MaterialTheme.shapes.large
    ) {
        // UI med søkefunksjonalitet på toppen
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = { searchViewModel.onSearchQueryChanged(it) },
                onSearchTriggered = {
                    searchViewModel.triggerSearch()

                    // Åpne bottom sheet når søket trigges
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    }
}
