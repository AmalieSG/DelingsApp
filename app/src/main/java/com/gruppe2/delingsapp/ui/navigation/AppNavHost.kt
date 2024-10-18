package com.gruppe2.delingsapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gruppe2.delingsapp.ui.screens.PaymentOptionsScreen
import com.gruppe2.delingsapp.ui.screens.ReturnProductPage
//import com.gruppe2.delingsapp.ui.screens.VippsPaymentScreen
import com.gruppe2.delingsapp.ui.screens.VisaPaymentScreen
import com.gruppe2.delingsapp.screens.VippsPaymentScreen
//import com.gruppe2.delingsapp.screens.VippsPaymentScreen
import com.gruppe2.delingsapp.ui.CameraActivity
import com.gruppe2.delingsapp.ui.screens.*
import com.gruppe2.delingsapp.ui.navigation.routes.NavbarRoutes
import com.gruppe2.delingsapp.ui.navigation.routes.ScreenRoutes
import com.gruppe2.delingsapp.ui.screens.productScreens.EditProductScreen
import com.gruppe2.delingsapp.ui.screens.productScreens.ProductScreen
//import com.example.myapplication.viewmodel.ProductViewModel
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import com.gruppe2.delingsapp.ui.screens.HomePage
import com.gruppe2.delingsapp.ui.screens.LoginScreen
import com.gruppe2.delingsapp.ui.screens.ProfileScreen
import com.gruppe2.delingsapp.ui.screens.RegisterScreen
//import com.gruppe2.delingsapp.ui.screens.advertisement.AdvertisementViewModel
import com.gruppe2.delingsapp.ui.screens.advertisement.AdvertisementScreen
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import com.gruppe2.delingsapp.viewmodel.AdvertisementViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel,
    advertisementViewModel: AdvertisementViewModel<Any?>, //Asn testing
    modifier: Modifier = Modifier,
) {
    val bottomNavItems = listOf(
        NavbarRoutes.Home,
        NavbarRoutes.Advertisement, // asn testing
        NavbarRoutes.Login,
        NavbarRoutes.Profile,
        NavbarRoutes.Register,
        NavbarRoutes.Product
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
            composable(ScreenRoutes.Advertisement.route) {
                AdvertisementScreen("", navController, advertisementViewModel)
            }

            /*composable(ScreenRoutes.Advertisement.route) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                AdvertisementScreen(username, navController, advertisementViewModel)
            } */

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
                HomePage()
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

        }


    }
}
