package com.app.commondata.repository

import com.app.commondata.api.ApiService
import com.app.commondata.room.BreedDao
import com.app.commondata.room.BreedEntity
import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * Repository module for handling data operations.
 *
 */
class DogsRepositoryImpl @Inject constructor(
    private val breedDao: BreedDao,
    private val apiService: ApiService,
) : DogsRepository {

    override suspend fun fetchDogBreeds(): List<BreedModel> {
        val response = apiService.fetchDogBreeds()
        when (response.code()) {
            HttpURLConnection.HTTP_OK -> {
                return response.body()?.message?.flatMap { (breed, types) ->
                    val hasFavorite = findFavorite(breed.replace(" ", ""))
                    if (types.isEmpty())
                        listOf(
                            BreedModel(breed, "", hasFavorite)
                        )
                    else
                        types.map {
                            BreedModel(breed, it, hasFavorite)
                        }
                } ?: emptyList()
            }

            else -> throw IllegalStateException("Failed to load, Please try again")
        }
    }

    override suspend fun fetchDogBreedImages(breed: String, subBreed: String): List<String> {
        val response = if (subBreed.isNotEmpty())
            apiService.fetchDogSubBreedImages(breed, subBreed)
        else apiService.fetchDogBreedImages(breed)
        when (response.code()) {
            HttpURLConnection.HTTP_OK -> {
                return response.body()?.message ?: emptyList()
            }

            else -> throw IllegalStateException("Failed to load, Please try again")
        }
    }

    override fun getFavoriteBreed(): Flow<List<BreedModel>> =
        breedDao.getFavoriteBreeds().map { it.map { it.transform() } }

    override suspend fun saveToFavorite(breedModel: BreedModel) =
        breedDao.insert(BreedEntity(breedModel))

    override suspend fun removeFromFavorite(breedModel: BreedModel) =
        breedDao.delete(BreedEntity(breedModel))

    override suspend fun findFavorite(symbol: String): Boolean =
        breedDao.findWatchlist(symbol) != null
}
