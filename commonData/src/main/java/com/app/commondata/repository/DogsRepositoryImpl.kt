package com.app.commondata.repository

import com.app.commondata.api.ApiService
import com.app.commondata.api.apiCall
import com.app.commondata.mapper.DogBreedsResponseMapper
import com.app.commondata.room.BreedDao
import com.app.commondata.room.BreedEntity
import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository module for handling data operations.
 *
 */
class DogsRepositoryImpl @Inject constructor(
    private val breedDao: BreedDao,
    private val apiService: ApiService,
    private val mapper: DogBreedsResponseMapper,
) : DogsRepository {

    override suspend fun fetchDogBreeds(): Result<List<BreedModel>> {
        return apiCall(
            call = { apiService.fetchDogBreeds() },
            mapper = { mapper.map(it.message) }
        )
    }

    override suspend fun fetchDogBreedImages(
        breed: String,
        subBreed: String
    ): Result<List<String>> {
        return apiCall(
            call = {
                if (subBreed.isNotEmpty()) apiService.fetchDogSubBreedImages(breed, subBreed)
                else apiService.fetchDogBreedImages(breed)
            },
            mapper = { it.message }
        )
    }

    override fun getFavoriteBreed(): Flow<List<BreedModel>> =
        breedDao.getFavoriteBreeds().map { it.map(BreedEntity::transform) }

    override suspend fun saveToFavorite(breedModel: BreedModel) =
        breedDao.insert(BreedEntity(breedModel))

    override suspend fun removeFromFavorite(breedModel: BreedModel) =
        breedDao.delete(BreedEntity(breedModel))

    override suspend fun findFavorite(symbol: String): Boolean =
        breedDao.findWatchlist(symbol) != null
}
