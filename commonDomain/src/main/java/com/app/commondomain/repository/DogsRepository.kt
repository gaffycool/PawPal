package com.app.commondomain.repository

import com.app.commondomain.model.BreedModel
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun fetchDogBreeds(): Result<List<BreedModel>>
    suspend fun fetchDogBreedImages(breed: String, subBreed: String): Result<List<String>>
    fun getFavoriteBreed(): Flow<List<BreedModel>>
    suspend fun saveToFavorite(breedModel: BreedModel)
    suspend fun removeFromFavorite(breedModel: BreedModel)
    suspend fun findFavorite(symbol: String): Boolean
}
