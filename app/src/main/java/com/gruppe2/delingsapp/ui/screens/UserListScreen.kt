package com.gruppe2.delingsapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.gruppe2.delingsapp.viewmodel.User

@Composable
fun UserListScreen(navController: NavController, userViewModel: UserViewModel, currentUserId: String) {
    var users by remember { mutableStateOf(listOf<User>()) }
    val db = FirebaseFirestore.getInstance()

    // Fetch all users from Firestore
    LaunchedEffect(Unit) {
        db.collection("users").get().addOnSuccessListener { snapshot ->
            val userList = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            // Filter out the current user
            users = userList.filter { it.userId != currentUserId }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Select a user to chat with", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(users) { user ->
                UserItem(user, onClick = {
                    // Navigate to chat screen with the selected user's userId
                    navController.navigate("chat/${user.userId}")
                })
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(text = user.username, fontSize = 16.sp)
    }
}