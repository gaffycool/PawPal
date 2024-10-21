package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import javax.inject.Inject

class FetchComparisonDataInteractor @Inject constructor(
    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor
) {
    suspend fun get(params: Params): ComparisonDataModel = ComparisonDataModel(
        firstBreedImages = fetchBreedImagesInteractor.get(
            FetchBreedImagesInteractor.Params(
                params.firstBreedData.breed,
                params.firstBreedData.subBreed,
            )
        ),
        secondBreedImages = fetchBreedImagesInteractor.get(
            FetchBreedImagesInteractor.Params(
                params.secondBreedData.breed,
                params.secondBreedData.subBreed,
            )
        )
    )

    data class Params(
        val firstBreedData: BreedModel,
        val secondBreedData: BreedModel
    )
}

data class ComparisonDataModel(
    val firstBreedImages: List<String>,
    val secondBreedImages: List<String>,
)