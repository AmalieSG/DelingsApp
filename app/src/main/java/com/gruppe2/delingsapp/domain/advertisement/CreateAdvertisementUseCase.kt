package com.gruppe2.delingsapp.domain.advertisement

class CreateAdvertisementUseCase(
    private val repository: AdvertisementRepository,
    private val authRepository: AuthRepository
) {
    suspend fun execute(
        title: String, description: String, location: String, category: String,
        selectedProducts: List<Product>, availability: Pair<String, String>?
    ): Result<AdvertisementModel> {
        // Handle advertisement creation logic, validation, etc.
        if (!authRepository.isUserSignedIn()) {
            return Result.failure(Exception("User not signed in"))
        }

        val ad = AdvertisementModel(
            id = UUID.randomUUID(),
            owner = authRepository.getCurrentUserId(),
            title = title,
            description = description,
            location = location,
            category = category,
            products = selectedProducts,
            availability = availability
        )
        return repository.addAdvertisement(ad)
    }
}
