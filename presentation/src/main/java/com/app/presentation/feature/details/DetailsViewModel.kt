package com.app.presentation.feature.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.FetchBreedImagesInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.base.BaseViewModel
import com.app.presentation.base.InteractorRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor,
    private val interactorRunner: InteractorRunner,
) : BaseViewModel<DetailsViewState>() {
    private val detailsArgs: DetailsArgs = DetailsArgs(savedStateHandle)

    override fun defaultState(): DetailsViewState = DetailsViewState()

    init {
        newState { it.copy(breedModel = detailsArgs.breedData) }
        fetchBreedDetails(detailsArgs.breedData)
    }

    @VisibleForTesting
    fun fetchBreedDetails(breedData: BreedModel) {
        interactorRunner(
            job = viewModelScope,
            action = {
                fetchBreedImagesInteractor.invoke(
                    FetchBreedImagesInteractor.Params(
                        breedData.breed,
                        breedData.subBreed
                    )
                )
            },
            onSuccess = { breeds ->
                newState { it.copy(breedImages = breeds) }
            }
        )
    }
}

data class DetailsViewState(
    val breedModel: BreedModel? = null,
    val breedImages: List<String> = emptyList(),
)
