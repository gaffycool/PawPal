package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interactor to fetch favorite
 *
 */
class GetFavoriteBreedInteractor @Inject constructor(
    private val muscleRepository: DogsRepository
) {
    operator fun invoke(): Flow<List<BreedModel>> = muscleRepository.getFavoriteBreed()
}
