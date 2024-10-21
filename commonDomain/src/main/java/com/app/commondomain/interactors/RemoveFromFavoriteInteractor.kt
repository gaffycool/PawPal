package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import com.app.commondomain.model.BreedModel
import javax.inject.Inject

/**
 * Interactor to remove from favorite list
 *
 */
class RemoveFromFavoriteInteractor @Inject constructor(
    private val muscleRepository: DogsRepository
) {
    suspend fun remove(model: BreedModel) =
        muscleRepository.removeFromFavorite(model)
}
