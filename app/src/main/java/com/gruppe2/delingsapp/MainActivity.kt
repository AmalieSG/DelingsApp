package com.gruppe2.delingsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gruppe2.delingsapp.ui.navigation.AppNavHost
import com.gruppe2.delingsapp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val userViewModel: UserViewModel = viewModel()  // Del UserViewModel mellom skjermene

            // Kall AppNavHost og send inn både navController og userViewModel
            AppNavHost(
                navController = navController,
                userViewModel = userViewModel  // Legg til userViewModel som parameter
            )
        }
    }
}
