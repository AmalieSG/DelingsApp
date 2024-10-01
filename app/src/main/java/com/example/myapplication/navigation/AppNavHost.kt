package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.HomePage
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.navigation.NavBar
import com.example.myapplication.navigation.ScreenRoutes

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val bottomNavItems = listOf(
        ScreenRoutes.Home,
        ScreenRoutes.Login,
        ScreenRoutes.Profile,
        ScreenRoutes.Register
    )
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
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
        composable(ScreenRoutes.Home.route) {
            HomePage()  // Viser forsiden når brukeren navigerer hit
        }
    }
    NavBar(navController = navController, items = bottomNavItems)
}
