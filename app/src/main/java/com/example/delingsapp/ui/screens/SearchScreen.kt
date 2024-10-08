package com.example.delingsapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delingsapp.ui.components.Product
import kotlinx.coroutines.launch
import com.example.delingsapp.viewmodel.SearchViewModel
import com.example.delingsapp.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    val query by searchViewModel.query.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState(initial = emptyList())
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()

    // Show bottom sheet when search is initiated
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { showBottomSheet = false }
            },
        sheetState = bottomSheetState
    ) {
        // Bottom sheet content
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Products", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)

            LazyColumn {
                items(filteredProducts) { product ->
                    ProductScreen(product) // Assume you have a ProductItem composable
                    Divider()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChange = { newQuery -> searchViewModel.updateQuery(newQuery) },
                onSearch = {
                    showBottomSheet = true
                    scope.launch { bottomSheetState.show() }
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { paddingValues ->
        // Main content
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            // Any other UI components can go here
        }
    }
}
