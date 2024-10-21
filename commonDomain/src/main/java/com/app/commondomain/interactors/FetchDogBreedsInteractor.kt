package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import javax.inject.Inject

class FetchDogBreedsInteractor @Inject constructor(
    private val dogsRepository: DogsRepository
) {
    suspend fun get(): List<BreedModel> = dogsRepository.fetchDogBreeds()
}
