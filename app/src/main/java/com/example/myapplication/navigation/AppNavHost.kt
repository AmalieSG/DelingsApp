package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.HomeScreen
import com.example.myapplication.LoginScreen
import com.example.myapplication.ui.screens.RegisterScreen

@Composable
fun AppNavHost(
    navController: NavHostController,  // Bruker NavController fra navigasjon
    modifier: Modifier = Modifier       // Tar en Modifier som parameter med en default-verdi
) {
    // Selve NavHost som håndterer navigasjonen mellom forskjellige skjermbilder
    NavHost(
        navController = navController,
        startDestination = "home",      // Start-skjermen er "home"
        modifier = modifier             // Bruker modifier her for å håndtere padding
    ) {
        composable("home") { HomeScreen(navController) }  // Naviger til HomeScreen
        composable("login") { LoginScreen(navController) }  // Naviger til LoginScreen
        composable("register") { RegisterScreen(navController) }  // Naviger til LoginScreen
    }
}
