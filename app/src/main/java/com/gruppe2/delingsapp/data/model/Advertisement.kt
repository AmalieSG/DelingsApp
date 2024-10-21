/*package com.gruppe2.delingsapp.data.model

import android.icu.util.DateInterval
import android.location.Geocoder
import android.provider.MediaStore.Images
import java.util.Locale.Category




// Represents an advertisement, which includes a list of products
data class Advertisement(
    val id: Int, // Unique identifier for each ad
    val owner: String, // Owner of the advertisement
    val name: String, // Advertisement name/title
    val description: String, // Description of the advertisement
    val location: String, // Location where the product is available
    val category: String, // Category of the product
    val products: List<Product>, // List of products included in this advertisement
    val photos: List<String> = emptyList() // Additional photos related to the ad
)




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


*/