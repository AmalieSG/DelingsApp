package com.gruppe2.delingsapp.ui.screens.Annonse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.gruppe2.delingsapp.ui.screens.ScrollableContent
import com.gruppe2.delingsapp.viewmodel.User
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class AnnonseScreen {

}


@Composable
fun AnnonseScreen (username: String?, userViewModel: UserViewModel, navController: NavController) {
    var user by remember { mutableStateOf<User?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

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
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                " Tester annonse"
            )
        }
    }

}

