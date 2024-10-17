package com.gruppe2.delingsapp.data.service

import android.util.Log  // Import for logging
import com.google.firebase.messaging.FirebaseMessagingService  // Import for FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage  // Import for RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")  // Log the new token
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message received: ${remoteMessage.notification?.body}")
        // Handle the received message
    }
}
