/*package com.example.myapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.io.File
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class CameraViewModel : ViewModel() {
    private lateinit var imageCapture: ImageCapture

    fun startCamera(lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(lifecycleOwner.context)

        cameraProviderFuture.addListener({
            // Bli med i CameraX-setup
            val cameraProvider = cameraProviderFuture.get()

            // Opprett en Preview
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // Konfigurer ImageCapture
            imageCapture = ImageCapture.Builder().build()

            // Velg kamera (bakhodet kamera)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Koble sammen komponentene
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        }, ContextCompat.getMainExecutor(lifecycleOwner.context))
    }

    // Ta et bilde
    fun takePicture(onImageCaptured: (File) -> Unit) {
        val outputOptions = ImageCapture.OutputFileOptions.Builder(createFile()).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedFile = outputFileResults.savedUri?.let { File(it.path) }
                if (savedFile != null) {
                    onImageCaptured(savedFile) // Returner den lagrede filen
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraViewModel", "Error capturing image: ${exception.message}")
            }
        })
    }

    // Funksjon for å lage en fil for bildet
    private fun createFile(): File {
        // Implementer logikk for å opprette en fil for bildet, for eksempel
        val storageDir = File(context.getExternalFilesDir(null), "pictures")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File(storageDir, "${System.currentTimeMillis()}.jpg")
    }
}
*/