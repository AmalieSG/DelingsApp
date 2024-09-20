package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.viewmodel.UserViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,  // Legg til userViewModel som parameter her
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController, userViewModel)  // Send userViewModel til LoginScreen
        }
        composable("register") {
            RegisterScreen(navController, userViewModel)  // Send userViewModel til RegisterScreen
        }
        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            ProfileScreen(username, userViewModel)  // Send userViewModel til ProfileScreen
        }
    }
}
