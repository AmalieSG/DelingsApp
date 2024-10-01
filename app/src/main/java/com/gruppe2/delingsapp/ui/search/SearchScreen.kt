package com.gruppe2.delingsapp.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gruppe2.delingsapp.data.model.SearchBar

@Composable
fun SearchScreen(viewModel: SearchBarViewModel = viewModel()) {
    val searchQuery = viewModel.searchQuery.observeAsState("")

    Column {
        SearchBar(
            placeholder = "Search products...",
            onSearch = { query ->
                viewModel.updateSearchQuery(query) // Oppdater søkestrengen i ViewModel
            }
        )

        // Her kan du bruke `searchQuery.value` til å filtrere elementene som vises
    }
}
