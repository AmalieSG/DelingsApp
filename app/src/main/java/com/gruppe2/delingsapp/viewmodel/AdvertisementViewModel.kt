package com.gruppe2.delingsapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.gruppe2.delingsapp.data.model.AdvertisementModel
import com.gruppe2.delingsapp.data.model.Product
import com.gruppe2.delingsapp.data.repository.AdvertisementRepository
import com.gruppe2.delingsapp.shared.model.Product.AuthRepository
import java.util.UUID


/* The ViewModel will handle the logic of retrieving and managing the data.
It communicates with a repository to get the list of advertisements and prepares
it in a way that the UI can display.
 */


// Versjon - enkel, men bruker parcelize i Adv.Model

class AdvertisementViewModel(
    private val advertisementRepository: AdvertisementRepository,
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
            val productsList = if (selectedProducts.isEmpty()) null else selectedProducts.toList() // Handle empty selection
            val advertisement = AdvertisementModel(
                id = UUID.randomUUID(),
                owner = authRepository.getCurrentUserId(),
                title = title,
                description = description,
                location = location,
                category = category,
                products = selectedProducts.toList(), // Optional; å velge produkter fra liste. Sender en tom liste til AdvertisementViewModel, dersom ingen produkter er valgt.
                availability = availability
            )
            advertisementRepository.addAdvertisement(advertisement)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onFailure(exception) }
        } else {
            onFailure(Exception("User not signed in"))
        }
    }

    // List of products selected for the advertisement
    /*var selectedProducts = mutableStateListOf<Product>()
        private set */

    // Function to add a product to the selected list
    fun addProduct(product: Product) {
        if (!selectedProducts.contains(product)) {
            selectedProducts.add(product)
        }
    }

    // Function to remove a product from the selected list
    fun removeProduct(product: Product) {
        selectedProducts.remove(product)
    }

    // Function to clear all selected products (optional)
    fun clearSelectedProducts() {
        selectedProducts.clear()
    }

    // Function to check if the user selected no products
    fun hasSelectedProducts(): Boolean {
        return selectedProducts.isNotEmpty()
    }

    // Function som brukes i "ProductSelectionScreen" for å hente en brukers liste med produkter
    fun getMyProducts(): Int {
    // TODO: Implementer en funksjon som henter produkter som er lagt til i min liste.
    }
}

private fun Any.addOnFailureListener(any: Any) {

}

private fun Any.addOnSuccessListener(function: () -> Unit): Any {
    TODO("Not yet implemented")
}


/*
// Denne versjonen henter inn CreateAdvUsecase
class AdvertisementViewModel(

    // AdvertisementViewModel benytter en CreateAdvertisementUseCase som holder på logikk for hvordan en advertisement objekt skal defineres
    private val createAdvertisementUseCase: CreateAdvertisementUseCase

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

    // Forslag 1,
    fun createAdvertisement(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        // Sjekker først om bruker innlogget = true TODO: Sjekk om denne benytter firebase User.Id
        if (authRepository.isUserSignedIn()) {
            val advertisement = AdvertisementModel(
                id = UUID.randomUUID().toString(),
                owner = authRepository.getCurrentUserId(),
                title = title,
                description = description,
                location = location,
                category = category,
                //products = selectedProducts.toList(), // Can be empty as default
                products = selectedProducts, // Can be empty as default
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


 */












