package com.gruppe2.delingsapp.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavbarRoutes(val route: String, val title: String, val icon: ImageVector,val hasArgs: Boolean) {
    object Home : NavbarRoutes("home", "Home", Icons.Filled.Home, false)
    object Profile : NavbarRoutes("profile/{username}", "Profile", Icons.Filled.Person, true)
    object Login : NavbarRoutes("login", "Login", Icons.Filled.Add, false)

    // Create an advertisement, based on user.id, if !logedIn ( route to "singInScreen")
    object Advertisement : NavbarRoutes(
        "profile/{username}/advertisement",
        "New Ad from Profile",
        Icons.Filled.AddCircle,
        true
    )

    object CreateAdvertisement :
        NavbarRoutes("createNewAdvertisement", "New Ad", Icons.Filled.AddCircle, true)
    object ownedProducts : NavbarRoutes("listProducts", "My Products", Icons.Filled.Info,false)
    object AddProduct : NavbarRoutes("addProduct", "AddProduct", Icons.Filled.Add,false)
    object Messages : NavbarRoutes("messages", "Messages", Icons.Filled.Add,false)
}
    /* GAMMEL
    sealed class NavbarRoutes(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavbarRoutes("home", "Home", Icons.Filled.Home)
    // Create an advertisement, based on user.id, if !logedIn ( route to "singInScreen")
    object Advertisement : NavbarRoutes("profile/{username}/advertisement", "New Ad", Icons.Filled.AddCircle)
    object CreateAdvertisement : NavbarRoutes("createNewAdvertisement", "New Ad", Icons.Filled.AddCircle)
    object Profile : NavbarRoutes("profile/{username}", "Profile", Icons.Filled.Person)
    object Login : NavbarRoutes("login", "Login", Icons.Filled.Add)
    object Register : NavbarRoutes("register", "Register", Icons.Filled.Add)
    object Product : NavbarRoutes("product/Slagdrill", "Product(test)", Icons.Filled.Add)


    }

     */
