package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.CameraActivity
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
        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            ProfileScreen(username, userViewModel, navController)
        }
        composable("users") {
            UserListScreen(navController, userViewModel)
        }

        composable("checklist") {
            ReturnChecklistPage(navController)
        }

        composable("chat/{recipientUserId}") { backStackEntry ->
            val recipientUserId = backStackEntry.arguments?.getString("recipientUserId") ?: ""
            MessageScreen(
                navController = navController,
                currentUserId = userViewModel.currentUserId ?: "",
                recipientUserId = recipientUserId
            )
        }

        composable("return_product") {
            ReturnProductPage(navController)
        }

        // Route for PaymentOptionsScreen
        composable("payment_options") {
            PaymentOptionsScreen(navController)
        }

        // Route for VisaPaymentScreen, passing currentUserId
        composable("visa_payment") {
            VisaPaymentScreen(navController)
        }

        // Route for VippsPaymentScreen, passing currentUserId
        composable("vipps_payment") {
            VippsPaymentScreen(navController)
        }

        composable("camera") {
            CameraActivity()
        }
    }
}
