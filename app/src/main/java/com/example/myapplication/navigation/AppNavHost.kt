package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.*
import com.example.myapplication.viewmodel.UserViewModel

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
        composable("profile") {
            ProfileScreen(navController, userViewModel)
        }
        composable("users") {
            UserListScreen(navController, userViewModel)
        }

        // Use ProductsPage instead of MainPage
        composable("products") {
            ProductsPage()  // Navigate to ProductsPage, where products are displayed
        }

        composable("chat/{recipientUserId}") { backStackEntry ->
            val recipientUserId = backStackEntry.arguments?.getString("recipientUserId") ?: ""
            MessageScreen(
                navController = navController,
                currentUserId = userViewModel.currentUserId ?: "", // Assuming currentUserId is available in UserViewModel
                recipientUserId = recipientUserId
            )
        }

    }
}
