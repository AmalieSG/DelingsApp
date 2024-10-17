package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*  // Import necessary Composables
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.ui.screens.ScrollableContent
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import com.gruppe2.delingsapp.viewmodel.User
import com.gruppe2.delingsapp.ui.navigation.routes.ScreenRoutes
import com.gruppe2.delingsapp.ui.screens.productScreens.ReserveProductScreen
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(username: String?, userViewModel: UserViewModel, navController: NavController) {
    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(username) {
        if (username != null) {
            coroutineScope.launch {
                userViewModel.getUserByUsername(username) { result ->
                    if (result != null) {
                        user = result
                    } else {
                        errorMessage = "Bruker ikke funnet"
                    }
                }
            }
        }
    }

    ScrollableContent {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user != null) {
                // Profile section with user icon
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Name and rating
                Text(
                    text = "Welcome, ${user?.username}",  // Display username from Firestore
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating section with star icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Rating", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Star,  // Star icon
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Profile buttons
                ProfileButton(
                    text = "Rediger profil",
                    onClick = { /* Handle profile edit */ },
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                ProfileButton(text = "Mine annonser", onClick = { /* Handle ads */ }, navController)
                Spacer(modifier = Modifier.height(16.dp))
                ProfileButton(
                    text = "Definere responstid",
                    onClick = { /* Handle response time */ },
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                ProfileButton(
                    text = "Sette tidsrom for tilgjengelighet",
                    onClick = { /* Handle availability */ },
                    navController
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Navigate to ProductsPage
                Button(
                    onClick = {
                        navController.navigate("products") {
                            popUpTo("products") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Forside", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate(ScreenRoutes.UserList.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Messages", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Log out button
                Button(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logg ut", color = MaterialTheme.colorScheme.onError)
                }
            } else {
                // If user not found, display error message
                Text(
                    text = errorMessage.ifEmpty { "Brukerdata ikke funnet" },
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Button to navigate to ReturnProductPage
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("return_product") }) {
                Text("Go to Return Product Screen")
            }


            Spacer(modifier = Modifier.height(16.dp))
            ProfileButton(text = "Go to payment", onClick = {
                navController.navigate("payment_options") // Navigate to payment screen page
            }, navController)
        }
    }
}

@Composable
fun ProfileButton(text: String, onClick: () -> Unit, navController: NavController) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFFB8860B),
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Color(0xFFB8860B))
    ) {
        Text(text)
    }
}
