package com.gruppe2.delingsapp.viewmodel

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.gruppe2.delingsapp.data.model.AdvertisementModel
import com.gruppe2.delingsapp.data.model.Product
import com.gruppe2.delingsapp.data.repository.AdvertisementRepository


    // Logic to filter advertisements or transform the data for the UI can go here
// Med authentication
/*
class AdvertisementViewModel(
    private val repository: AdvertisementRepository,
    private val authRepository: AuthRepository // For checking if user is authenticated
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var location by mutableStateOf("")
    var category by mutableStateOf("")
    var selectedProducts = mutableStateListOf<Product>()
    var availability by mutableStateOf<Pair<String, String>?>(null) // Start and end dates

    // Function to create an advertisement
    fun createAdvertisement(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (authRepository.isUserSignedIn()) {
            val advertisement = AdvertisementModel(
                id = UUID.randomUUID().toString(),
                owner = authRepository.getCurrentUserId(),
                title = title,
                description = description,
                location = location,
                category = category,
                products = selectedProducts,
                availability = availability
            )
            repository.addAdvertisement(advertisement)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onFailure(exception) }
        } else {
            onFailure(Exception("User not signed in"))
        }
    }
}
//
*/


// Forslag fra chatgpt, impl firebase og løsning for å lagre på en annen måte

class AdvertisementViewModel<Advertisement>(
    private val repository: AdvertisementRepository? = null // For local or other storage
) : ViewModel(), Parcelable {

    // Firebase Firestore instance
    private val db: FirebaseFirestore = Firebase.firestore

    // LiveData to observe advertisements from repository
    val advertisements: LiveData<List<Advertisement>>? = repository?.getAdvertisements()

    // MutableStateList to hold advertisements fetched from Firebase
    private val firebaseAdvertisements = mutableStateListOf<AdvertisementModel>()

    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AdvertisementViewModel<Any?>> {
        override fun createFromParcel(parcel: Parcel): AdvertisementViewModel<Any?> {
            return AdvertisementViewModel(parcel)
        }

        override fun newArray(size: Int): Array<AdvertisementViewModel<Any?>?> {
            return arrayOfNulls(size)
        }
    }

    // Function to fetch advertisements from Firebase Firestore
    fun fetchAdvertisementsFromFirebase() {
        db.collection("advertisements")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ad = document.toObject(AdvertisementModel::class.java)
                    firebaseAdvertisements.add(ad)
                }
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    // Function to create an advertisement and store it in Firebase Firestore
    fun createAdvertisementInFirebase(newAd: AdvertisementModel) {
        db.collection("advertisements")
            .add(newAd)
            .addOnSuccessListener {
                firebaseAdvertisements.add(newAd)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    // Function to create an advertisement and store it using AdvertisementRepository
    fun createAdvertisementLocally(
        owner: String,
        name: String,
        description: String,
        location: String,
        category: String,
        selectedProducts: List<Product>
    ) {
        val newAd = AdvertisementModel(
            id = (advertisements?.value?.size?.plus(1) ?: 1).toString(), // Auto-generate ID
            owner = owner,
            title = title,
            description = description,
            location = location,
            category = category,
            products = selectedProducts
        )
        repository?.addAdvertisement(newAd)
    }

    // Logic to combine or decide between Firebase and Repository data
    fun getCombinedAdvertisements(): List<AdvertisementModel> {
        // Combine advertisements from both sources (Firebase and repository)
        val combinedList = mutableListOf<AdvertisementModel>()
        firebaseAdvertisements.let { combinedList.addAll(it) }
        advertisements?.value?.let { combinedList.addAll(it) }
        return combinedList
    }
}



/*
class AdvertisementViewModel() : ViewModel(), Parcelable {
    private val db: FirebaseFirestore = Firebase.firestore

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdvertisementViewModel> {
        override fun createFromParcel(parcel: Parcel): AdvertisementViewModel {
            return AdvertisementViewModel(parcel)
        }

        override fun newArray(size: Int): Array<AdvertisementViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
*/

/*
// List to hold advertisements
val advertisements = mutableStateListOf<AdvertisementModel<Any?>>()

// Function to create an advertisement
fun createAdvertisement(
    owner: String,
    name: String,
    description: String,
    location: String,
    category: String,
    selectedProducts: List<Product>
) {
    val newAd = AdvertisementModel(
        id = advertisements.size + 1, // Auto-generate ID
        owner = owner,
        name = name,
        description = description,
        location = location,
        category = category,
        products = selectedProducts
    )
    advertisements.add(newAd) // Add the new advertisement to the list
}
*/