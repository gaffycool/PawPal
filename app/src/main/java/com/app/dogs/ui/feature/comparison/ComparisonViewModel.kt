package com.app.dogs.ui.feature.comparison

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.FetchComparisonDataInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComparisonViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchComparisonDataInteractor: FetchComparisonDataInteractor,
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
        viewModelScope.launch {
            val data = fetchComparisonDataInteractor.get(
                FetchComparisonDataInteractor.Params(firstBreedData, secondBreedData)
            )
            newState {
                it.copy(
                    firstBreedData = it.firstBreedData.copy(breedImages = data.firstBreedImages),
                    secondBreedData = it.secondBreedData.copy(breedImages = data.secondBreedImages)
                )
            }
        }
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