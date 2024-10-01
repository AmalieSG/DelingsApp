package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*  // Importer nødvendige Composables
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*  // Importer Material3 for UI-komponenter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

@Composable
fun MessageScreen(navController: NavController, currentUserId: String, recipientUserId: String) {
    val db = FirebaseFirestore.getInstance()
    val chatId = getChatId(currentUserId, recipientUserId) // Unik ID for samtalen

    // States for meldingene
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var newMessage by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    // Hente meldinger fra Firebase Firestore
    LaunchedEffect(chatId) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    messages = snapshot?.documents?.mapNotNull { it.toObject(Message::class.java) } ?: emptyList()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false // Viser meldinger fra toppen
        ) {
            items(messages.size) { index ->
                MessageItem(messages[index], currentUserId)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                scope.launch {
                    if (newMessage.text.isNotEmpty()) {
                        sendMessage(db, chatId, currentUserId, newMessage.text)
                        newMessage = TextFieldValue("") // Tøm inputfeltet etter sending
                    }
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, currentUserId: String) {
    val isSentByCurrentUser = message.sender == currentUserId
    Row(
        horizontalArrangement = if (isSentByCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = message.text,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .background(if (isSentByCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
        )
    }
}

// Modell for meldinger
data class Message(
    val sender: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

// Funksjon for å sende meldinger til Firestore
fun sendMessage(db: FirebaseFirestore, chatId: String, senderId: String, messageText: String) {
    val newMessage = Message(sender = senderId, text = messageText)
    db.collection("chats").document(chatId).collection("messages")
        .add(newMessage)
}

// Funksjon for å generere en unik ID for chatten basert på bruker-IDer
fun getChatId(userId1: String, userId2: String): String {
    return if (userId1 < userId2) "$userId1_$userId2" else "$userId2_$userId1"
}
