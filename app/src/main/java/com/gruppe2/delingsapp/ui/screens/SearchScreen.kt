package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gruppe2.delingsapp.R
import com.gruppe2.delingsapp.components.ProductList
import com.gruppe2.delingsapp.ui.components.SearchBar
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import com.gruppe2.delingsapp.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    productViewModel: ProductViewModel,
    query: String
) {
    val searchViewModel = remember { SearchViewModel(productViewModel) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    )
    val coroutineScope = rememberCoroutineScope()
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState()

    LaunchedEffect(query) {
        productViewModel.fetchAllProducts()
        searchViewModel.onSearchQueryChanged(query)
        searchViewModel.triggerSearch()
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            if (filteredProducts.isEmpty()) {
                Text(
                    text = "No products found",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                ProductList(products = filteredProducts)
            }
        },
        sheetPeekHeight = 100.dp,
        sheetShape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (filteredProducts.isNotEmpty()) {
                // TODO: Bytt ut med Google Maps
                Image(
                    painter = painterResource(id = R.drawable.map_background_image),
                    contentDescription = "Map Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SearchBar(
                    searchQuery = searchQuery,
                    onQueryChange = { searchViewModel.onSearchQueryChanged(it) },
                    onSearchTriggered = {
                        searchViewModel.triggerSearch()

                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        }
    }
}
