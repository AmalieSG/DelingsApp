package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.*
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.navigation.NavBar
import com.example.myapplication.navigation.ScreenRoutes
import com.example.myapplication.ui.CameraActivity

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController, userViewModel)
        }
        composable("register") {
            RegisterScreen(navController, userViewModel)
        }
        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            ProfileScreen(username, userViewModel, navController) // Pass the username to ProfileScreen
        }
        composable("users") {
            UserListScreen(navController, userViewModel)
        }

/*
        // Use ProductsPage instead of MainPage
        composable("products") {
            ProductsScreen()  // Navigate to ProductsPage, where products are displayed
        }
*/

        composable("checklist") {
            ReturnChecklistPage(navController)  // Naviger til sjekklisten
        }

        composable("chat/{recipientUserId}") { backStackEntry ->
            val recipientUserId = backStackEntry.arguments?.getString("recipientUserId") ?: ""
            MessageScreen(
                navController = navController,
                currentUserId = userViewModel.currentUserId ?: "", // Assuming currentUserId is available in UserViewModel
                recipientUserId = recipientUserId
            )
        }
        composable("return_product") { // New route for ReturnProductPage
            ReturnProductPage(navController)
        }

        composable("camera") {
            CameraActivity()
        }

    }
}
