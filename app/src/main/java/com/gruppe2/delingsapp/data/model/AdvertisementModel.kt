package com.gruppe2.delingsapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.auth.User
import com.gruppe2.delingsapp.viewmodel.UserViewModel
import kotlinx.parcelize.Parcelize
import java.util.UUID


// Data model for Advertisement
// bruke parcelize for  å slippe boiler plate kode: https://developer.android.com/kotlin/parcelize
@Parcelize
data class AdvertisementModel(
    val id: UUID, // Unique identifier for the advertisement
    val owner: User.Id = "", // User ID of the owner creating the advertisement
    val title: String = "", // Title of the advertisement
    val description: String = "", // Description of the advertisement
    val location: String = "", // Location of the item being advertised
    val category: String = "", // Category the advertisement belongs to
    val products: List<Product> = listOf(), // List of products in the advertisement
    val availability: Pair<String, String>? = null, // Availability period (start and end dates)
    val photos: List<String> = listOf() // URLs or paths to photos associated with the advertisement
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }
}


/*
// Represents an advertisement, which includes a list of products
// Forenklet versjon (asn testing 18.okt)
data class AdvertisementModel<userId>(
    val id: Int, // Unique identifier for each ad
    //val idString: String, //asn Testing
    val owner: userId, // Owner of the advertisement
    val title: String, // Advertisement name/title
    val description: String, // Description of the advertisement
    //val location: String, // Location where the product is available
    val category: String, // Category of the product
    val products: List<Product>, // List of products included in this advertisement
    //val photos: List<String> = emptyList() // Additional photos related to the ad
)

*/


// An advertisement, which can hold on a list of products that are already created by the user.
/*data class Advertisement(
    val id: Int,
    val owner:String, //TODO: knytte opp med UserID (asn)
    val title: String,
    val description: String,
    val availability: Boolean, //Holds the status if a advertisement is available to rent or not
    val price: Double,
    val deposit: Double,
    val image: String? = null, //TODO: Finn ut hvordan bruke bilder, håndtere mtp processer og kapasitet (asn)
    val location: String, //Geocoder, //TODO: Undersøke hvordan implementere geolokasjon
    val reviews: Int, //TODO: Bli enige om hvordan vi skal implementere reveiws - et tall fra 1-10 - tilknyttet "advertisement.id" eller "bruker.id"?
    val category: String, //Category, //Enum
    val products: List<Product> = emptyList() // Additional photos user can add to their advertisement
) */


