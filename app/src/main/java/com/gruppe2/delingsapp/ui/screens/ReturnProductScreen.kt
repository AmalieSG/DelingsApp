package com.gruppe2.delingsapp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.gruppe2.delingsapp.R
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Composable
fun ReturnProductScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) } // URL of the uploaded image
    val context = LocalContext.current

    // Launcher for capturing the image
    val cameraLauncher = rememberLauncherForActivityResult(
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

    // Request permission to use the camera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Launch the camera when permission is granted
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(takePictureIntent)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
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
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    // Launch the camera directly since permission is granted
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(takePictureIntent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ta bilde av produktet")
        }

        // Display the image if available
        imageUri?.let { uri ->
            Image(
                bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .size(200.dp) // Set size of the displayed image
                    .padding(16.dp)
            )

            // Button to use the captured image
            Button(
                onClick = {
                    // Upload image to Firebase Storage
                    uploadImageToFirebase(uri, context) { downloadUrl ->
                        imageUrl = downloadUrl
                        Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Bruk dette bildet")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Go back button
        Button(onClick = { navController.popBackStack() }) {
            Text("Go back")
        }
    }
}

// Function to upload image to Firebase Storage
private fun uploadImageToFirebase(imageUri: Uri, context: android.content.Context, onSuccess: (String) -> Unit) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg") // Unique file name

    imageRef.putFile(imageUri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString()) // Return the download URL
            }
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
