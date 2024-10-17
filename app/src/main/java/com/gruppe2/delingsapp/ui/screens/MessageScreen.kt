package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessageScreen(currentUserId: String, recipientUserId: String) {
    val db = FirebaseFirestore.getInstance()
    val chatId = getChatId(currentUserId, recipientUserId)

    var messages by remember { mutableStateOf(listOf<Message>()) }
    var newMessage by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(chatId) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error == null) {
                    messages = snapshot?.documents?.mapNotNull { it.toObject(Message::class.java) } ?: emptyList()
                    Log.d("ChatMessages", "Fetched messages: $messages")
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false // Show latest messages at the bottom
        ) {
            items(messages) { message ->
                MessageItem(message, currentUserId, messages)
            }
        }

        MessageInputSection(
            newMessage = newMessage,
            onMessageChange = { newMessage = it },
            onSendClick = {
                scope.launch {
                    if (newMessage.text.isNotEmpty()) {
                        sendMessage(db, chatId, currentUserId, newMessage.text)
                        newMessage = TextFieldValue("") // Clear input after sending
                    }
                }
            }
        )
    }
}

@Composable
fun MessageItem(message: Message, currentUserId: String, messages: List<Message>) {
    val isSentByCurrentUser = message.sender == currentUserId

    // Get the date for the current message
    val currentMessageDate = formatDate(message.timestamp)
    // Get the date for the previous message if it exists
    val previousMessageDate = if (messages.indexOf(message) > 0) {
        formatDate(messages[messages.indexOf(message) - 1].timestamp)
    } else {
        ""
    }

    // Show date only if it's different from the previous message's date
    if (currentMessageDate != previousMessageDate) {
        Text(
            text = currentMessageDate,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp) // Add some padding for the date
        )
    }

    Row(
        horizontalArrangement = if (isSentByCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(if (isSentByCurrentUser) Color.Blue else Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = if (isSentByCurrentUser) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatTimestamp(message.timestamp),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MessageInputSection(
    newMessage: TextFieldValue,
    onMessageChange: (TextFieldValue) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = newMessage,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        )
        Button(onClick = onSendClick) {
            Text("Send")
        }
    }
}

// Model for messages
data class Message(
    val sender: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now() // Ensure timestamp is a Timestamp object
)

// Function to send messages to Firestore
fun sendMessage(db: FirebaseFirestore, chatId: String, senderId: String, messageText: String) {
    val newMessage = Message(
        sender = senderId,
        text = messageText,
        timestamp = Timestamp.now() // Store timestamp as Timestamp
    )
    db.collection("chats").document(chatId).collection("messages").add(newMessage)
}

// Function to generate a unique ID for the chat between two users
fun getChatId(userId1: String, userId2: String): String {
    return if (userId1 < userId2) "$userId1$userId2" else "$userId2$userId1"
}

// Function to format timestamp
fun formatTimestamp(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    hourFormat.timeZone = TimeZone.getTimeZone("CET") // Set timezone to CET
    return hourFormat.format(date)
}

// Function to format date
fun formatDate(timestamp: Timestamp): String {
    val dateFormat = SimpleDateFormat("EEEE, d. MMMM", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("CET") // Set timezone to CET
    return dateFormat.format(timestamp.toDate())
}


