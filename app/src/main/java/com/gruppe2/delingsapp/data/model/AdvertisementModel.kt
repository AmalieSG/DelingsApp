package com.gruppe2.delingsapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.auth.User
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.util.UUID


// Data model for Advertisement
// Bruker parcelize for  å slippe boiler plate kode: https://developer.android.com/kotlin/parcelize
@Parcelize
data class AdvertisementModel(
    val id: UUID, // Unique identifier for the advertisement
    // val id: String = "", //Midlertidig løsning
    val owner: String = "", // User ID of the owner creating the advertisement //TODO: knytte opp med UserID (asn)
    val title: String = "", // Title of the advertisement
    val description: String = "", // Description of the advertisement
    val location: String = "", // Location of the item being advertised //TODO: Undersøke hvordan implementere geolokasjon
    val category: String = "", // Category the advertisement belongs to
    // val products: List<Product>, // versjon 1: Denne er IKKE verbose og krever IKKE  konstruktør, dersom man ikke bruker Parcelize
    // val products: List<Product>? = null, // versjon2: Gjør det optional å velge produkter fra List of products. (nullable to indicate optional)
    val products: List<Product> = listOf(), // Versjon 3: Gjør det OPTIONAL for bruker å velge prod. fra sin liste. Empty list by default i stedet for 'null'
    val availability: Pair<String, String>? = null, // Availability period (start and end dates)
    val photos: List<String> = listOf() // URLs or paths to photos associated with the advertisement //TODO: Finn ut hvordan bruke bilder, håndtere mtp processer og kapasitet (asn)
    // val reviews: Int, //TODO: Bli enige om hvordan vi skal implementere reveiws - et tall fra 1-10 - tilknyttet "advertisement.id" eller "bruker.id"?

) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object : Parceler<AdvertisementModel> {
        override fun AdvertisementModel.write(p0: Parcel, p1: Int) {
            TODO("Not yet implemented")
        }

        override fun create(parcel: Parcel): AdvertisementModel = TODO()
    }
}





