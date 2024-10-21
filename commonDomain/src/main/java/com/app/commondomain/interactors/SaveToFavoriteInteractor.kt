package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import com.app.commondomain.model.BreedModel
import javax.inject.Inject

/**
 * Interactor to save into favorite list
 *
 */
class SaveToFavoriteInteractor @Inject constructor(
    private val muscleRepository: DogsRepository
) {
    suspend fun save(watchlistModel: BreedModel) = muscleRepository.saveToFavorite(watchlistModel)
}
