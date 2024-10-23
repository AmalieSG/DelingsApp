package com.gruppe2.delingsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime


data class Reservation(
    val renterId: String = "",
    val startDateTime: String = "",
    val endDateTime: String  = "",

)

data class Product(
    val name: String = "",
    val ownerId: String? = "",
    val description: String = "",
    val price: Double = 0.0,
    val photos: List<String> = mutableListOf(),
    val location: String = "",
    //val category: List<String> = mutableListOf(),
    val category: String = "",
    val reservations: List<Reservation> = mutableListOf()
)

class ProductViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

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


    fun addProduct(product: com.gruppe2.delingsapp.viewmodel.Product) {
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



    fun fetchAllProducts() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { result: QuerySnapshot ->
                val productList = result.toObjects(Product::class.java)
                _products.value = productList
            }
            .addOnFailureListener { e: Exception ->
                println("Feil ved henting av produkter: ${e.message}")
                _products.value = emptyList()
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

    fun isProductRented(product: Product?, desiredReservationTime: String): Boolean? {
        return product?.reservations?.any { reservation ->
            LocalDateTime.parse(desiredReservationTime).isAfter(LocalDateTime.parse(reservation.startDateTime)) &&
                    LocalDateTime.parse(desiredReservationTime).isBefore(LocalDateTime.parse(reservation.endDateTime)
            )
        }
    }

   suspend fun reserveProduct(productName:String, renterId:String, startDateTime: String, endDateTime: String) : Boolean {
       val productId = getProductId(productName)
       if (productId != null) {
           val product = getProduct(productName)

           product?.let {

               if (isProductRented(it, startDateTime) == false) {

                   val updatedReservations = it.reservations.toMutableList().apply {
                       add(Reservation(renterId, startDateTime.toString(), endDateTime.toString()))
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

    suspend fun removeReservation(productName: String, renterId: String, startDateTime: String, endDateTime: String): Boolean {
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