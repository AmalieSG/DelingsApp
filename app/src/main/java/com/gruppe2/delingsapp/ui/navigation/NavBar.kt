package com.gruppe2.delingsapp.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.gruppe2.delingsapp.ui.navigation.routes.NavbarRoutes
import com.gruppe2.delingsapp.ui.navigation.routes.ScreenRoutes
import com.gruppe2.delingsapp.viewmodel.UserViewModel

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: String
)



@Composable
fun NavBar(navController: NavHostController, items: List<NavbarRoutes>,userViewModel: UserViewModel) {

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = false,
                onClick = {
                    if (!item.hasArgs) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        userViewModel.getCurrentUser { user ->
                            val routeWithUsername = user?.let { item.route.replace("{username}", it.username) }
                            if (routeWithUsername != null) {
                                navController.navigate(routeWithUsername) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else {
                                navController.navigate(NavbarRoutes.Login.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}