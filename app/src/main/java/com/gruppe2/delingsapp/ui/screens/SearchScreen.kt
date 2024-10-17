package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gruppe2.delingsapp.ui.components.ProductCard
import com.example.myapplication.viewmodel.SearchViewModel
import com.example.myapplication.components.SearchBar

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel()) {
    val query by searchViewModel.query.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState(initial = emptyList())
    val isSearchInitiated by searchViewModel.isSearchInitiated.collectAsState(initial = false)

    Column {
        SearchBar(
            query = query,
            onQueryChange = { newQuery ->
                searchViewModel.updateQuery(newQuery)
            },
            onSearch = { searchViewModel.initiateSearch() }
        )

        if (isSearchInitiated) {
            LazyColumn {
                items(filteredProducts) { product ->
                    ProductCard(product = product)
                }
                if (filteredProducts.isEmpty()) {
                    item {
                        Text(
                            text = "Ingen produkter funnet",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
