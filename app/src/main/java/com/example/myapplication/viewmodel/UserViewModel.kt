package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuthException

// Brukerdata-modell
data class User(
    val userId: String = "", // Legg til userId her
    val username: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val rating: Float = 0f
)


class UserViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = Firebase.firestore

    val currentUserId: String?
        get() = auth.currentUser?.uid

    // Funksjon for å registrere en ny bruker med e-post og passord
    fun register(email: String, password: String, username: String, phoneNumber: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val newUser = User(username = username, phoneNumber = phoneNumber, email = email)

                    // Lagre brukerdata i Firestore
                    if (userId != null) {
                        db.collection("users").document(userId).set(newUser)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    callback(true, null)
                                } else {
                                    callback(false, firestoreTask.exception?.message)
                                }
                            }
                    } else {
                        callback(false, "User ID is null")
                    }
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    // Funksjon for å logge inn bruker
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)  // Login var vellykket
                } else {
                    // Sjekk for spesifikke Firebase-authentication-feilkoder
                    val errorCode = (task.exception as? FirebaseAuthException)?.errorCode
                    val errorMessage = when (errorCode) {
                        // Fanger opp spesifikke feil for ugyldig e-post eller passord
                        "ERROR_USER_NOT_FOUND", "ERROR_WRONG_PASSWORD", "ERROR_INVALID_EMAIL" -> {
                            "Incorrect email or password"
                        }
                        else -> {
                            "Wrong mail or password"
                        }
                    }
                    callback(false, errorMessage)
                }
            }
    }

    // Funksjon for å hente brukerdata basert på UID
    fun getCurrentUser(callback: (User?) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(User::class.java)
                    callback(user)
                }
                .addOnFailureListener {
                    callback(null)
                }
        } else {
            callback(null)
        }
    }
}
