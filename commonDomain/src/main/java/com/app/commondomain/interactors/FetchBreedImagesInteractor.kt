package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import javax.inject.Inject

class FetchBreedImagesInteractor @Inject constructor(
    private val dogsRepository: DogsRepository
) {
    suspend fun get(params: Params): List<String> = dogsRepository.fetchDogBreedImages(
        params.breed,
        params.subBreed
    )

    data class Params(
        val breed: String,
        val subBreed: String,
    )
}
