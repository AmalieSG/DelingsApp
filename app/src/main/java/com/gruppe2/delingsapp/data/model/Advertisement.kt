package com.gruppe2.delingsapp.data.model

import android.icu.util.DateInterval
import android.location.Geocoder
import android.provider.MediaStore.Images
import androidx.compose.runtime.Immutable
import java.util.Locale.Category

@Immutable
data class Advertisement(
    val id: Long,
    val owner: Int, //TODO: knytte opp med UserID (asn)
    val title: String,
    val description: String,
    val availability: DateInterval,
    val price: Int,
    val deposit: Int,
    val image: Images, //TODO: Finn ut hvordan bruke bilder, håndtere mtp processer og kapasitet (asn)
    val location: Geocoder, //TODO: Undersøke hvordan implementere geolokasjon
    val reviews: Int, //TODO: Bli enige om hvordan vi skal implementere reveiws - et tall fra 1-10 - tilknyttet "advertisement.id" eller "bruker.id"?
    val category: Category, //Enum
    val products: List<Product>
)


