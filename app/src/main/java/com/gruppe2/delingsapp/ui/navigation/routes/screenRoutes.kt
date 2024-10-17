package com.gruppe2.delingsapp.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenRoutes(val route: String, val title: String) {
    object Home : ScreenRoutes("home", "Home")
    object Profile : ScreenRoutes("profile/{username}", "Profile")
    object Login : ScreenRoutes("login", "Login")
    object Register : ScreenRoutes("register", "Register")
    object Product : ScreenRoutes("product/{productName}", "Product(test)")
    object UpdateProduct : ScreenRoutes("updateProduct/{productName}", "UpdateProduct(test)")
    object AddProduct : ScreenRoutes("addProduct", "AddProduct")
    object ReserveProduct : ScreenRoutes("reserveProduct/{productName}", "AddProduct")
    object ListProducts : ScreenRoutes("listProducts", "ListProducts")
    object UserList : ScreenRoutes("user_list", "User List")
    object Chat : ScreenRoutes("chat/{recipientUserId}", "Chat")
    object ReturnProduct : ScreenRoutes("return_product", "ReturnProduct")
    object PaymentOptions : ScreenRoutes("payment_options","Payment_options")
    object VisaPayment : ScreenRoutes("visa_payment","Visa_payment")
    object VippsPayment : ScreenRoutes("vipps_payment","Vipps_payment")
    object Camera : ScreenRoutes("camera","Camera")
}

