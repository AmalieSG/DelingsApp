package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.viewmodel.UserViewModel

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
