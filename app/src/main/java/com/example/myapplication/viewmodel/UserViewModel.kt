package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class UserViewModel : ViewModel() {
    // Variabler for å lagre brukernavn og passord
    var registeredUsername by mutableStateOf<String?>(null)
    var registeredPassword by mutableStateOf<String?>(null)

    // Funksjon for å registrere en bruker
    fun registerUser(username: String, password: String) {
        registeredUsername = username
        registeredPassword = password
    }

    // Funksjon for å verifisere login-legitimasjon
    fun login(username: String, password: String): Boolean {
        return registeredUsername == username && registeredPassword == password
    }
}
