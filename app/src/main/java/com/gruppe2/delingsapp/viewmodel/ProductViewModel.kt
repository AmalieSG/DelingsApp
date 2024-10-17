package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.gruppe2.delingsapp.ui.components.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime


data class Reservation(
    val renterId: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
)

data class Product(
    val name: String = "",
    val ownerId: String? = "",
    val description: String = "",
    val price: Double = 0.0,
    val photos: List<String> = mutableListOf(),
    val location: String = "",
    val category: String = "",
    val reservations: List<Reservation> = mutableListOf()
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

    suspend fun getAllProducts(): List<Product> {
        return try {
            val result = db.collection("Products").get().await()
            result.toObjects(Product::class.java)
        } catch (e: Exception) {
            println("Feil ved henting av produkter: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAllOwnedProducts(ownerId: String?): List<Product> {
        return try {
            val result = db.collection("Products").whereEqualTo("ownerId", ownerId).get().await()
            result.toObjects(Product::class.java)
        } catch (e: Exception) {
            println("Feil ved henting av produkter: ${e.message}")
            emptyList()
        }
    }

    fun isProductRented(product: Product?,desiredReservationTime:LocalDateTime): Boolean? {
        return product?.reservations?.any { reservation ->
            desiredReservationTime.isAfter(reservation.startDateTime) && desiredReservationTime.isBefore(
                reservation.endDateTime
            )
        }
    }

   suspend fun reserveProduct(productName:String, renterId:String, startDateTime:LocalDateTime, endDateTime:LocalDateTime) : Boolean {
       val productId = getProductId(productName)
       if (productId != null) {
           val product = getProduct(productName)

           product?.let {

               if (isProductRented(it, startDateTime) == false) {

                   val updatedReservations = it.reservations.toMutableList().apply {
                       add(Reservation(renterId, startDateTime, endDateTime))
                   }
                   val updatedProduct = it.copy(reservations = updatedReservations)


                   updateProduct(updatedProduct)
                   println("Produkt reservert.")
                   return true
               } else {
                   println("Tidsrommet er allerede reservert.")
               }
           }
       } else {
           println("Kunne ikke finne produkt for reservasjon.")
       }
       return false
   }

    suspend fun removeReservation(productName: String, renterId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean {
        val productId = getProductId(productName)
        if (productId != null) {
            val product = getProduct(productName)

            product?.let {

                val updatedReservations = it.reservations.filterNot { reservation ->
                    reservation.renterId == renterId &&
                            reservation.startDateTime == startDateTime &&
                            reservation.endDateTime == endDateTime
                }


                if (updatedReservations.size != it.reservations.size) {
                    val updatedProduct = it.copy(reservations = updatedReservations)

                    updateProduct(updatedProduct)
                    println("Reservasjon fjernet.")
                    return true
                } else {
                    println("Reservasjonen ble ikke funnet.")
                }
            }
        } else {
            println("Kunne ikke finne produkt for å fjerne reservasjon.")
        }
        return false
    }
}