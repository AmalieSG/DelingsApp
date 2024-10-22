package com.gruppe2.delingsapp.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavbarRoutes(val route: String, val title: String, val icon: ImageVector,val hasArgs: Boolean) {
    object Home : NavbarRoutes("home", "Home", Icons.Filled.Home,false)
    object Profile : NavbarRoutes("profile/{username}", "Profile", Icons.Filled.Person,true)
    object Annonse : NavbarRoutes("annonse", "Annonse", Icons.Filled.Add,false) //asn
    object Login : NavbarRoutes("login", "Login", Icons.Filled.Add,false)
    object ownedProducts : NavbarRoutes("listProducts", "My Products", Icons.Filled.Info,false)
    object AddProduct : NavbarRoutes("addProduct", "AddProduct", Icons.Filled.Add,false)
    object Messages : NavbarRoutes("messages", "Messages", Icons.Filled.Add,false)
}