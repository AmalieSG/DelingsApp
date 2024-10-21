package com.gruppe2.delingsapp.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.gruppe2.delingsapp.data.model.AdvertisementModel
import com.gruppe2.delingsapp.data.model.Product

class AdvertisementRepository {
    fun addAdvertisement(advertisement: AdvertisementModel): Any {

        return TODO("Provide the return value")
    }

    // Initial sample data for products ( Dummy data)
    val sampleProducts = mutableStateListOf(
        Product(1, "Alice", "Lawn Mower", "A powerful lawn mower for gardening.", true, 15.0, 50.0, null),
        Product(2, "Bob", "Drill", "Electric drill with multiple speed settings.", true, 10.0, 30.0, null),
        Product(3, "Alice", "Ladder", "7-foot aluminum ladder.", true, 8.0, 25.0, null)
    )

}