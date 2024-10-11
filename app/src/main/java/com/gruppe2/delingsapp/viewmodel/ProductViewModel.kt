package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.gruppe2.delingsapp.ui.components.Product
import com.google.firebase.firestore.FirebaseFirestore
//import com.example.myapplication.components.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class Product(
    val name: String = "",
    val owner: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val photos: List<String> = mutableListOf(),
    val location: String = "",
    val category: String = "",
    val status: Boolean = false
)

class ProductViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore


    private suspend fun getProductId(name: String): String? {
        return try {
            val querySnapshot = db.collection("Products")
                .whereEqualTo("name", name)
                .get()
                .await()
            if (querySnapshot.documents.isNotEmpty()) {
                querySnapshot.documents[0].id
            } else {
                null
            }
        } catch (e: Exception) {
            println("Kunne ikke finne noen id.")
            null
        }
    }


    fun addProduct(product: Product) {
        db.collection("Products").document().set(product).addOnSuccessListener {
            println("Produkt lagt til")
        }.addOnFailureListener {
            println("Kunne ikke legge til produkt")
        }
    }

    suspend fun removeProduct(product: Product) {
        val productId = getProductId(product.name)
        if (productId != null) {
            db.collection("Products").document(productId).delete().await()
            println("Produkt fjernet")
        } else {
            println("Kunne ikke finne produkt for å fjerne.")
        }
    }

    suspend fun updateProduct(product: Product) {
        val productId = getProductId(product.name)
        if (productId != null) {
            db.collection("Products").document(productId).set(product).await()
            println("Produkt oppdatert")
        } else {
            println("Kunne ikke finne produkt for oppdatering.")
        }
    }

    //getProduct er fra chatgpt
    suspend fun getProduct(name: String): Product? {
        return try {
            val productId = getProductId(name)
            if (productId != null) {
                val documentSnapshot = db.collection("Products")
                    .document(productId)
                    .get()
                    .await()
                documentSnapshot.toObject(Product::class.java)
            } else {
                println("Kunne ikke finne produkt med navnet: $name")
                null
            }
        } catch (e: Exception) {
            println("Feil ved henting av produkt: ${e.message}")
            null
        }
    }
}