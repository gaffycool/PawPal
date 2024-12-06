package com.app.commondata.mapper

import com.app.commondata.room.BreedDao
import com.app.commondomain.model.BreedModel
import javax.inject.Inject

class DogBreedsResponseMapper @Inject constructor(private val breedDao: BreedDao) {

    private suspend fun findFavorite(symbol: String): Boolean =
        breedDao.findWatchlist(symbol) != null

    suspend fun map(breeds: Map<String, List<String>>?): List<BreedModel> {
        return breeds?.flatMap { (breed, types) ->
            val hasFavorite = findFavorite(breed.replace(" ", ""))
            if (types.isEmpty())
                listOf(BreedModel(breed, "", hasFavorite))
            else
                types.map { BreedModel(breed, it, hasFavorite) }
        } ?: emptyList()
    }
}