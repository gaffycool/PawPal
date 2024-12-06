package com.app.presentation.feature.comparison

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.FetchComparisonDataInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.base.BaseViewModel
import com.app.presentation.base.InteractorRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComparisonViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchComparisonDataInteractor: FetchComparisonDataInteractor,
    private val interactorRunner: InteractorRunner,
) : BaseViewModel<ComparisonViewState>() {
    private val comparisonArgs: ComparisonArgs = ComparisonArgs(savedStateHandle)

    override fun defaultState(): ComparisonViewState = ComparisonViewState()

    init {
        newState {
            it.copy(
                firstBreedData = BreedImagesUiModel(comparisonArgs.firstBreedData),
                secondBreedData = BreedImagesUiModel(comparisonArgs.secondBreedData)
            )
        }
        fetchBreedDetails(comparisonArgs.firstBreedData, comparisonArgs.secondBreedData)
    }

    @VisibleForTesting
    fun fetchBreedDetails(firstBreedData: BreedModel, secondBreedData: BreedModel) {
        interactorRunner(
            job = viewModelScope,
            action = {
                fetchComparisonDataInteractor.invoke(
                    FetchComparisonDataInteractor.Params(firstBreedData, secondBreedData)
                )
            },
            onSuccess = { data ->
                newState {
                    it.copy(
                        firstBreedData = it.firstBreedData.copy(breedImages = data.firstBreedImages),
                        secondBreedData = it.secondBreedData.copy(breedImages = data.secondBreedImages)
                    )
                }
            }
        )
    }
}

data class ComparisonViewState(
    val firstBreedData: BreedImagesUiModel = BreedImagesUiModel(),
    val secondBreedData: BreedImagesUiModel = BreedImagesUiModel(),
)

data class BreedImagesUiModel(
    val breedModel: BreedModel? = null,
    val breedImages: List<String> = emptyList(),
)