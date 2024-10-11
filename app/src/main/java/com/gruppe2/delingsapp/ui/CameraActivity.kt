package com.gruppe2.delingsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.common.util.concurrent.ListenableFuture

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraScreen() // Call to your Composable function
        }
    }
}

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    LaunchedEffect(Unit) {
        cameraProvider = cameraProviderFuture.get()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Camera", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Display the camera preview
        AndroidView(factory = { ctx ->
            PreviewView(ctx).apply {
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(this.surfaceProvider)

                // Bind the camera to the lifecycle
                cameraProvider?.let {
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    it.bindToLifecycle(context as CameraActivity, cameraSelector, preview) // Corrected reference
                }
            }
        }, modifier = Modifier.fillMaxSize())

        Spacer(modifier = Modifier.height(16.dp))

        // Button to capture an image or take another action
        Button(onClick = { /* Handle capture or other action */ }) {
            Text("Capture Image")
        }
    }
}
