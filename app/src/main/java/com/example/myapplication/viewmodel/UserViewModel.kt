package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

data class User(val username: String, val password: String)

class UserViewModel : ViewModel() {
    private val users = mutableStateListOf<User>()

    // Funksjon for å registrere en ny bruker
    fun registerUser(username: String, password: String): Boolean {
        if (users.any { it.username == username }) {
            return false // Brukernavn eksisterer allerede
        }
        val newUser = User(username, password)
        users.add(newUser)
        println("Bruker registrert: $newUser")  // Logger registrert bruker
        return true
    }

    // Funksjon for å logge inn en bruker
    fun login(username: String, password: String): Boolean {
        users.forEach { println("Registered user: $it") }  // Print all registered users
        val matchingUser = users.find { it.username == username && it.password == password }
        if (matchingUser != null) {
            println("Login successful for user: $matchingUser")
            return true
        } else {
            println("Login failed for user: $username")
            return false
        }
    }


    // Funksjon for å hente en bruker basert på brukernavn
    fun getUserByUsername(username: String?): User? {
        return users.find { it.username == username }
    }

}
