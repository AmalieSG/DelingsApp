package com.example.myapplication.ui.screens

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.R
@Composable
fun ReturnProductPage(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) } // State for holding the image URI
    val context = LocalContext.current

    // Launcher for capturing the image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Image captured successfully, get the image URI
                imageUri = result.data?.data
                Toast.makeText(context, "Image captured successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Retur Produkt",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Button to take a picture
        Button(
            onClick = {
                // Launch camera intent
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                launcher.launch(takePictureIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Use an image from drawable instead of the icon
            Image(
                painter = painterResource(id = R.drawable.camera_icon), // Replace with your drawable resource
                contentDescription = "Take photo",
                modifier = Modifier.size(24.dp) // Set the size of the image as needed
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ta bilde av produktet")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the captured image if available
        imageUri?.let { uri ->
            Image(
                bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .size(200.dp) // Set size of the displayed image
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chat med selger
        Button(
            onClick = { /* Handle chat action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Chat med selger")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Returner produkt
        Button(
            onClick = {
                navController.navigate("camera") // Assuming you have a route set up for the CameraActivity
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Open Camera")
        }

    }
}
