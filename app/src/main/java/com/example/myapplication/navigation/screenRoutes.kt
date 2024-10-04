package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenRoutes(val route: String, val title: String, val icon: ImageVector) {
    object Home : ScreenRoutes("home", "Home", Icons.Filled.Home)
    object Profile : ScreenRoutes("profile/{username}", "Profile", Icons.Filled.Person)
    object Login : ScreenRoutes("login", "Login", Icons.Filled.Add)
    object Register : ScreenRoutes("register", "Register", Icons.Filled.Add)
}