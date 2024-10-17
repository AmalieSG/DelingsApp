package com.example.myapplication.navigation.routes

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

}