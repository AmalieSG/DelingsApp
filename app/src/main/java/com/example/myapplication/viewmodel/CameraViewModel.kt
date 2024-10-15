package com.example.myapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.io.File

class CameraViewModel : ViewModel() {
    private lateinit var imageCapture: ImageCapture
    private var onImageCapturedListener: ((Uri) -> Unit)? = null

    fun startCamera(previewView: PreviewView, context: Context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview, imageCapture)
        }, ContextCompat.getMainExecutor(context))
    }

    // Setter lytteren for bildeopptak
    fun setOnImageCapturedListener(listener: (Uri) -> Unit) {
        onImageCapturedListener = listener
    }

    // Tar bilde
    fun takePicture(context: Context) {
        val outputOptions = ImageCapture.OutputFileOptions.Builder(createFile(context)).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(File(outputFileResults.savedUri?.path))
                onImageCapturedListener?.invoke(savedUri) // Varsler at bildet er tatt
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraViewModel", "Error capturing image: ${exception.message}")
            }
        })
    }

    private fun createFile(context: Context): File {
        val storageDir = File(context.getExternalFilesDir(null), "pictures")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File(storageDir, "${System.currentTimeMillis()}.jpg")
    }
}

