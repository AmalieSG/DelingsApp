package com.gruppe2.delingsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gruppe2.delingsapp.ui.navigation.AppNavHost
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val userViewModel: UserViewModel = viewModel()  // Del UserViewModel mellom skjermene
            val productViewModel: ProductViewModel = viewModel()

            // Kall AppNavHost og send inn både navController og userViewModel
            AppNavHost(
                navController = navController,
                userViewModel = userViewModel,
                productViewModel = productViewModel
            )
            
            FirebaseAuth.getInstance().signOut()
        }
    }
}
