package com.example.myapplication.ui.screens.productScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.routes.NavbarRoutes
import com.example.myapplication.navigation.routes.ScreenRoutes
import com.example.myapplication.viewmodel.Product
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun UserProductsScreen( navController: NavController, productViewModel: ProductViewModel, userViewModel: UserViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var products by remember { mutableStateOf(emptyList<Product>()) }


    LaunchedEffect(userViewModel.currentUserId) {
        if (userViewModel.currentUserId != null) {
            coroutineScope.launch {
                products = productViewModel.getAllOwnedProducts(userViewModel.currentUserId)
            }
        } else {
            println("kunne ikke finne bruker")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Dine produkter")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(products) { product ->
                ProductListItem(product, navController)
            }
        }
    }
}

@Composable
fun ProductListItem(product: Product, navController: NavController) {
    Card(onClick = {navController.navigate(ScreenRoutes.Product.route.replace("{productName}", product.name))},
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name)
            Text(text = "Pris: ${product.price} kr")
    }


    }
}