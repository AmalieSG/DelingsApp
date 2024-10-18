package com.gruppe2.delingsapp.domain.advertisement

import com.gruppe2.delingsapp.data.model.AdvertisementModel
import com.gruppe2.delingsapp.data.repository.AdvertisementRepository
import com.gruppe2.delingsapp.shared.model.Product.AuthRepository
import com.gruppe2.delingsapp.viewmodel.Product
import java.util.UUID
import javax.inject.Inject

/*
//Domain layer UseCase
// The domain layer contains the business logic. Here’s where the
// validation occurs, and the data is processed before saving it via the repository.
*/

class CreateAdvertisementUseCaseImpl @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val authRepository: AuthRepository // Sjekker for current user Id
) : CreateAdvertisementUseCase {

    // todo: hvorfor override her?
    override suspend fun execute(
        title: String,
        description: String,
        location: String,
        category: String,
        selectedProducts: List<Product>,
        availability: Pair<String, String>?
    ): Result<Unit> {

        // Validate the input
        if (title.isBlank() || description.isBlank() || location.isBlank()) {
            return Result.failure(IllegalArgumentException("All fields must be filled"))
        }

        // Create local var userID and check if user is signed in
        val userId = authRepository.getCurrentUserId() // Make sure user is logged in
            ?: return Result.failure(IllegalStateException("User not logged in"))

        // Create Advertisement Model
        val advertisement = AdvertisementModel(
            id = UUID.randomUUID(),
            owner = userId,
            title = title,
            description = description,
            location = location,
            category = category,
            products = selectedProducts,
            availability = availability
        )
        //return advertisementRepository.addAdvertisement(ad)

        // Call repository to save advertisement
        return try {
            advertisementRepository.addAdvertisement(advertisement)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

