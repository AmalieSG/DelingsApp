/*package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*  // Importer nødvendige Composables
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*  // Importer Material3 for UI-komponenter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
        // Øvre seksjon med brukerinfo og produktinfo
        UserInfoSection(navController)

        // Liste over meldinger
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

        // Sendemeldingsseksjonen nederst
        MessageInputSection(newMessage, onMessageChange = { newMessage = it }, onSendClick = {
            scope.launch {
                if (newMessage.text.isNotEmpty()) {
                    sendMessage(db, chatId, currentUserId, newMessage.text)
                    newMessage = TextFieldValue("") // Tøm inputfeltet etter sending
                }
            }
        })
    }
}

@Composable
fun UserInfoSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Tilbake-knapp og brukerinformasjon
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Profilbilde (bruk en mock-painter for nå)
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_report_image),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Username", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "+47 22 20 18 19", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Last seen 5 mins ago", fontSize = 12.sp, color = Color.Green)
            }
        }
        // Produktbeskrivelse og tilgjengelighet
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera), // Placeholder for produktbilde
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Beskrivelse av produkt", fontSize = 16.sp)
                Text(text = "Tilgjengelig", fontSize = 14.sp, color = Color.Green)
                Text(text = "60kr", fontSize = 14.sp)
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun MessageItem(message: Message, currentUserId: String) {
    val isSentByCurrentUser = message.sender == currentUserId
    Row(
        horizontalArrangement = if (isSentByCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(if (isSentByCurrentUser) Color.Blue else Color.LightGray)
                .padding(8.dp)
        ) {
            // Tidsstempel over meldingen
            Text(text = "kl. ${message.timestamp}", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(4.dp))
            // Selve meldingen
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = if (isSentByCurrentUser) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun MessageInputSection(newMessage: TextFieldValue, onMessageChange: (TextFieldValue) -> Unit, onSendClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Plus-ikon for å legge til filer (kan utvides senere)
        IconButton(onClick = { /* Add functionality to attach files */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add file")
        }

        // Meldingsinputfelt
        BasicTextField(
            value = newMessage,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        )

        // Send-knapp
        Button(onClick = onSendClick) {
            Text("Send a message")
        }
    }
}

// Modell for meldinger
data class Message(
    val sender: String = "",
    val text: String = "",
    val timestamp: String = ""  // Bruk String for enkelhet i visning
)

// Funksjon for å sende meldinger til Firestore
fun sendMessage(db: FirebaseFirestore, chatId: String, senderId: String, messageText: String) {
    val newMessage = Message(sender = senderId, text = messageText, timestamp = System.currentTimeMillis().toString())
    db.collection("chats").document(chatId).collection("messages")
        .add(newMessage)
}

// Funksjon for å generere en unik ID for chatten basert på bruker-IDer
fun getChatId(userId1: String, userId2: String): String {
    return if (userId1 < userId2) "$userId1_$userId2" else "$userId2_$userId1"
}


*/