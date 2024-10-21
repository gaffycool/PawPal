package com.app.dogs.ui.feature.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.FetchBreedImagesInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.ui.base.BaseViewModel
import com.app.dogs.ui.feature.comparison.ComparisonArgs
import com.app.dogs.ui.feature.comparison.ComparisonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor,
) : BaseViewModel<DetailsViewState>() {
    private val detailsArgs: DetailsArgs = DetailsArgs(savedStateHandle)

    override fun defaultState(): DetailsViewState = DetailsViewState()

    init {
        newState { it.copy(breedModel = detailsArgs.breedData) }
        fetchBreedDetails(detailsArgs.breedData)
    }

    @VisibleForTesting
    fun fetchBreedDetails(breedData: BreedModel) {
        viewModelScope.launch {
            val breeds = fetchBreedImagesInteractor.get(
                FetchBreedImagesInteractor.Params(
                    breedData.breed,
                    breedData.subBreed
                )
            )
            newState { it.copy(breedImages = breeds) }
        }
    }
}

data class DetailsViewState(
    val breedModel: BreedModel? = null,
    val breedImages: List<String> = emptyList(),
)
