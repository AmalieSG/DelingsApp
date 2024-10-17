package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.components.SearchBar
import com.example.myapplication.components.ProductList
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    productViewModel: ProductViewModel,
    query: String
) {

    val searchViewModel = remember { SearchViewModel(productViewModel) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded =  false)
    )
    val coroutineScope = rememberCoroutineScope()
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState()

    LaunchedEffect(query) {
        //productViewModel.fetchAllProducts()
        searchViewModel.onSearchQueryChanged(query)
        searchViewModel.triggerSearch()
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            ProductList(products = filteredProducts)
        },
        sheetPeekHeight = 100.dp,
        sheetShape = MaterialTheme.shapes.large
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.map_background_image), // Bytt til din nett-URL eller bruk `painterResource(R.drawable.your_image)` for lokale bilder
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

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