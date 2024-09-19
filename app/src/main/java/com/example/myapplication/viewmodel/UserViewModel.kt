package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class UserViewModel : ViewModel() {
    var registeredUsername by mutableStateOf<String?>(null)
    var registeredPassword by mutableStateOf<String?>(null)

    fun registerUser(username: String, password: String) {
        registeredUsername = username
        registeredPassword = password
    }

    fun login(username: String, password: String): Boolean {
        return registeredUsername == username && registeredPassword == password
    }
}
