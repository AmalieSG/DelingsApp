package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

// Brukerdata-modell (kan utvides med flere felt som rating, e-post osv.)
data class User(
    val username: String = "",
    val rating: Float = 0f
)

class UserViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance() // Firebase Authentication
    private val db: FirebaseFirestore = Firebase.firestore     // Firestore Database

    // Funksjon for å logge inn bruker med callback
    fun login(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null) // Login var vellykket
                } else {
                    callback(false, task.exception?.message) // Login feilet
                }
            }
    }

    // Funksjon for å registrere bruker med callback
    fun register(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Hvis registreringen er vellykket, lagre brukerdata i Firestore
                    val newUser = User(username = username, rating = 0f)  // Standard rating
                    val userId = auth.currentUser?.uid // Hent UID-en til den registrerte brukeren

                    if (userId != null) {
                        db.collection("users").document(userId)
                            .set(newUser)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    // Brukerdata lagret vellykket i Firestore
                                    callback(true, null)
                                } else {
                                    // Feil ved lagring i Firestore
                                    callback(false, firestoreTask.exception?.message)
                                }
                            }
                    } else {
                        // Feil hvis UID ikke kunne hentes (skulle egentlig ikke skje)
                        callback(false, "Unable to get user ID")
                    }
                } else {
                    // Registreringen feilet, returner feilmelding
                    callback(false, task.exception?.message)
                }
            }
    }

    // Funksjon for å hente brukerdata basert på brukernavn fra Firestore
    suspend fun getUserByUsername(username: String): User? {
        return try {
            // Hent brukerdata fra Firestore basert på brukernavn
            val snapshot = db.collection("users").whereEqualTo("username", username).get().await()
            if (snapshot.documents.isNotEmpty()) {
                // Konverter Firestore-dokumentet til User-objekt
                snapshot.documents[0].toObject(User::class.java)
            } else {
                null // Ingen bruker funnet
            }
        } catch (e: Exception) {
            null // Returner null hvis det oppstår en feil
        }
    }

    val currentUserId: String?
        get() = auth.currentUser?.uid  // Hent den nåværende brukerens UID fra FirebaseAuth
}
