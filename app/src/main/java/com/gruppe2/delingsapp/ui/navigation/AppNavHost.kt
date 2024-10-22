package com.gruppe2.delingsapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gruppe2.delingsapp.ui.CameraActivity
import com.gruppe2.delingsapp.ui.screens.*
import com.gruppe2.delingsapp.ui.navigation.routes.NavbarRoutes
import com.gruppe2.delingsapp.ui.navigation.routes.ScreenRoutes
import com.gruppe2.delingsapp.ui.screens.Annonse.AnnonseScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.EditProductScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.ProductScreen
//import com.example.myapplication.viewmodel.ProductViewModel
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import com.gruppe2.delingsapp.ui.screens.HomePage
import com.gruppe2.delingsapp.ui.screens.LoginScreen
import com.gruppe2.delingsapp.ui.screens.ProfileScreen
import com.gruppe2.delingsapp.ui.screens.RegisterScreen
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import com.gruppe2.delingsapp.ui.screens.productScreens.AddProductScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.ProductScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.ReserveProductScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.UserProductsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    //Legg screens som skal vises i navbaren her.
    val bottomNavItems = listOf(
        NavbarRoutes.Home,
        NavbarRoutes.Login,
        NavbarRoutes.Profile,
        NavbarRoutes.ownedProducts,
        NavbarRoutes.AddProduct
    )

    Scaffold(
        bottomBar = {
            NavBar(navController = navController, items = bottomNavItems, userViewModel)
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
            composable(ScreenRoutes.AddProduct.route) {
                val userId = userViewModel.currentUserId?:""
                AddProductScreen(userId,navController, productViewModel)
            }
            composable(ScreenRoutes.ListProducts.route) {
                UserProductsScreen(navController, productViewModel,userViewModel)
            }
            composable(ScreenRoutes.ReserveProduct.route) { backStackEntry ->
                val productName = backStackEntry.arguments?.getString("productName")
                ReserveProductScreen(productName,navController, productViewModel,userViewModel)
            }
            composable(ScreenRoutes.Profile.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                ProfileScreen(username, userViewModel, navController)
            }
            composable(ScreenRoutes.Product.route) {  backStackEntry ->
                val productName = backStackEntry.arguments?.getString("productName")
                ProductScreen(productName, navController, productViewModel,userViewModel)
            }

            composable(ScreenRoutes.UpdateProduct.route) {  backStackEntry ->
                val productName = backStackEntry.arguments?.getString("productName")
                EditProductScreen(productName, navController, productViewModel,userViewModel)
        }
            composable(ScreenRoutes.Home.route) {
                HomePage(
                    navController = navController,
                    //searchQuery = searchQuery.value,
                    //onQueryChange = { searchQuery.value = it }
                )
            }

            composable(ScreenRoutes.UserList.route) {
                val currentUserId = userViewModel.currentUserId ?: ""
                UserListScreen(navController, userViewModel, currentUserId)
            }


            composable(ScreenRoutes.Chat.route) { backStackEntry ->
                val recipientUserId = backStackEntry.arguments?.getString("recipientUserId") ?: ""
                MessageScreen(
                    currentUserId = userViewModel.currentUserId ?: "",
                    recipientUserId = recipientUserId
                )
            }

            composable(ScreenRoutes.ReturnProduct.route) {
                ReturnProductScreen(navController)
            }

            // Route for PaymentOptionsScreen
            composable(ScreenRoutes.PaymentOptions.route) {
                PaymentOptionsScreen(navController)
            }

            // Route for VisaPaymentScreen, passing currentUserId
            composable(ScreenRoutes.VisaPayment.route) {
                VisaPaymentScreen(navController)
            }

            // Route for VippsPaymentScreen, passing currentUserId
            composable(ScreenRoutes.VippsPayment.route) {
                VippsPaymentScreen(navController)
            }

            composable(ScreenRoutes.Camera.route) {
                CameraActivity()
            }

            composable("search?query={query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchScreen(productViewModel = productViewModel, query = query)
            }

            composable(ScreenRoutes.Annonse.route) {   backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                AnnonseScreen(username, userViewModel,navController)
            }
        }


    }
}
