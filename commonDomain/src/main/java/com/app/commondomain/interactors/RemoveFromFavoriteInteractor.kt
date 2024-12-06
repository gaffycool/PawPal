package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import javax.inject.Inject

/**
 * Interactor to remove from favorite list
 *
 */
class RemoveFromFavoriteInteractor @Inject constructor(
    private val muscleRepository: DogsRepository
) {
    suspend operator fun invoke(model: BreedModel) =
        muscleRepository.removeFromFavorite(model)
}
