package com.gruppe2.delingsapp.domain.advertisement

import com.gruppe2.delingsapp.viewmodel.Product



interface CreateAdvertisementUseCase {
    suspend fun execute(
        title: String,
        description: String,
        location: String,
        category: String,
        selectedProducts: List<Product>,  //Benytter Product fra viewmodel/Product
        availability: Pair<String, String>?
    ): Result<Unit>
}
