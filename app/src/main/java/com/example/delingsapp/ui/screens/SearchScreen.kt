package com.example.delingsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delingsapp.ui.components.SearchBar
import com.example.delingsapp.ui.components.ProductList
import com.example.delingsapp.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel()
) {
    // Husk BottomSheetState
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded =  false)
    )

    val coroutineScope = rememberCoroutineScope()

    val searchQuery by searchViewModel.searchQuery.collectAsState()
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
