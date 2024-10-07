package com.gruppe2.delingsapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gruppe2.delingsapp.ui.screens.HomePage
import com.gruppe2.delingsapp.ui.screens.LoginScreen
import com.gruppe2.delingsapp.ui.screens.ProfileScreen
import com.gruppe2.delingsapp.ui.screens.RegisterScreen
import com.gruppe2.delingsapp.viewmodel.UserViewModel

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
            composable(ScreenRoutes.Home.route) {
                HomePage()
            }
        }
    }
}
