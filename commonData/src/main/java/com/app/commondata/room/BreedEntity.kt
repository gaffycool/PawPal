package com.app.commondata.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.commondomain.model.BreedModel

@Entity(tableName = "breeds")
data class BreedEntity(
    val breed: String,
    val subBreed: String,
    @PrimaryKey val symbol: String,
    val isFavorite: Boolean = false
) {
    fun transform() = BreedModel(breed, subBreed, isFavorite)

    constructor(breedModel: BreedModel) : this(
        breed = breedModel.breed,
        subBreed = breedModel.subBreed,
        symbol = breedModel.symbol,
        isFavorite = breedModel.isFavorite
    )
}
