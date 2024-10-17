package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.screens.productScreens.AddProductScreen
import com.example.myapplication.ui.screens.productScreens.EditProductScreen
import com.example.myapplication.ui.screens.productScreens.ProductScreen
import com.example.myapplication.ui.screens.productScreens.ReserveProductScreen
import com.example.myapplication.ui.screens.productScreens.UserProductsScreen
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
        NavbarRoutes.ownedProducts,
        NavbarRoutes.AddProduct
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
                HomePage()
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
        }
    }
}
