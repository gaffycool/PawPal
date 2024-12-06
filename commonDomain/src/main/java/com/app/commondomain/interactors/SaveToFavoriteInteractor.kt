package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import javax.inject.Inject

/**
 * Interactor to save into favorite list
 *
 */
class SaveToFavoriteInteractor @Inject constructor(
    private val muscleRepository: DogsRepository
) {
    suspend operator fun invoke(watchlistModel: BreedModel) =
        muscleRepository.saveToFavorite(watchlistModel)
}
