package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.CameraActivity
import com.example.myapplication.ui.screens.*
import com.example.myapplication.navigation.routes.NavbarRoutes
import com.example.myapplication.navigation.routes.ScreenRoutes
import com.example.myapplication.ui.screens.HomePage
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.screens.productScreens.EditProductScreen
import com.example.myapplication.ui.screens.productScreens.ProductScreen
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.viewmodel.ProductViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val bottomNavItems = listOf(
        NavbarRoutes.Home,
        NavbarRoutes.Login,
        NavbarRoutes.Profile,
        NavbarRoutes.Register,
        NavbarRoutes.Product,
        NavbarRoutes.Search
    )

    Scaffold(
        bottomBar = {
            NavBar(navController = navController, items = bottomNavItems)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = modifier.padding(innerPadding)
        ) {
            composable(ScreenRoutes.Login.route) {
                LoginScreen(navController, userViewModel)
            }
            composable(ScreenRoutes.Register.route) {
                RegisterScreen(navController, userViewModel)
            }
            composable(ScreenRoutes.Profile.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                ProfileScreen(username, userViewModel, navController)
            }
           // composable(ScreenRoutes.Product.route) {  backStackEntry ->
              //  val productName = backStackEntry.arguments?.getString("productName")
               // ProductScreen(productName, navController, productViewModel)
            //}
            composable(ScreenRoutes.Product.route) {
                ProductScreen("Slagdrill", navController, productViewModel)
            }

            composable(ScreenRoutes.UpdateProduct.route) {  backStackEntry ->
                val productName = backStackEntry.arguments?.getString("productName")
                EditProductScreen(productName, navController, productViewModel)
        }
            composable(ScreenRoutes.Home.route) {
                HomePage(
                    navController = navController,
                    //searchQuery = searchQuery.value,
                    //onQueryChange = { searchQuery.value = it }
                )
            }
            composable("chat/{recipientUserId}") { backStackEntry ->
                val recipientUserId = backStackEntry.arguments?.getString("recipientUserId") ?: ""
               // TODO: Sørge for å importerer MessageScreen / løse feil (asn)
                MessageScreen(
                    navController = navController,
                    currentUserId = userViewModel.currentUserId ?: "",
                    recipientUserId = recipientUserId
                )
            }

            composable("return_product") {
                ReturnProductPage(navController)
            }

            // Route for PaymentOptionsScreen
            composable("payment_options") {
                PaymentOptionsScreen(navController)
            }

            // Route for VisaPaymentScreen, passing currentUserId
            composable("visa_payment") {
                VisaPaymentScreen(navController)
            }

            // Route for VippsPaymentScreen, passing currentUserId
            composable("vipps_payment") {
                VippsPaymentScreen(navController)
            }

            composable("camera") {
                CameraActivity()
            }

            composable("search?query={query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchScreen(productViewModel = productViewModel, query = query)
            }
        }
    }
}
