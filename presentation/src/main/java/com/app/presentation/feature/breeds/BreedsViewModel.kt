package com.app.presentation.feature.breeds

import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.FetchDogBreedsInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.interactors.SaveToFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.base.BaseViewModel
import com.app.presentation.base.InteractorRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject internal constructor(
    private val fetchDogBreedsInteractor: FetchDogBreedsInteractor,
    private val saveToFavoriteInteractor: SaveToFavoriteInteractor,
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor,
    private val interactorRunner: InteractorRunner,
) : BaseViewModel<BreedsViewState>() {

    override fun defaultState(): BreedsViewState = BreedsViewState()

    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        interactorRunner(
            job = viewModelScope,
            action = { fetchDogBreedsInteractor.invoke() },
            onSuccess = { breeds ->
                newState { it.copy(breeds = breeds) }
            }
        )
    }

    fun onValueChange(value: String) {
        newState { it.copy(searchValue = value, showDropdownMenu = value.isNotEmpty()) }
    }

    fun onDismissMenu() {
        newState { it.copy(showDropdownMenu = false) }
    }

    fun showSearchMenu(show: Boolean) {
        newState { it.copy(showSearchBar = show) }
    }

    fun enableComparison() {
        newState {
            it.copy(
                enableComparison = !it.enableComparison,
                comparingBreeds = emptyList()
            )
        }
    }

    fun actionFavorite(index: Int) = requireState { vmState ->
        val model = vmState.breeds[index]
        if (model.isFavorite) {
            interactorRunner(
                job = viewModelScope,
                action = { removeFromFavoriteInteractor.invoke(model) },
                onSuccess = {
                    newState {
                        vmState.copy(
                            breeds = vmState.breeds.toMutableList().apply {
                                set(index, model.copy(isFavorite = false))
                            }.toList()
                        )
                    }
                }
            )
        } else {
            val favoriteModel = model.copy(isFavorite = true)
            interactorRunner(
                job = viewModelScope,
                action = { saveToFavoriteInteractor.invoke(favoriteModel) },
                onSuccess = {
                    newState {
                        vmState.copy(
                            breeds = vmState.breeds.toMutableList().apply {
                                set(index, favoriteModel)
                            }.toList()
                        )
                    }
                }
            )
        }
    }

    fun selectBreed(model: BreedModel) = requireState { state ->
        if (state.comparingBreeds.contains(model)) {
            newState {
                it.copy(
                    comparingBreeds = it.comparingBreeds.toMutableList().apply {
                        remove(model)
                    }.toList()
                )
            }
        } else if (state.allowToSelectBreed) {
            newState { it.copy(comparingBreeds = it.comparingBreeds + model) }
        }
    }

    fun compareBreeds(callBack: (BreedModel, BreedModel) -> Unit) = requireState { state ->
        if (state.comparingBreeds.size == MAX_BREED_TO_COMPARE) {
            callBack(state.comparingBreeds.first(), state.comparingBreeds[1])
        }
    }

    companion object {
        const val MAX_BREED_TO_COMPARE = 2
    }
}

data class BreedsViewState(
    val breeds: List<BreedModel> = emptyList(),
    val comparingBreeds: List<BreedModel> = emptyList(),
    val searchValue: String = "",
    val showDropdownMenu: Boolean = false,
    val selectedBreed: String? = null,
    val showSearchBar: Boolean = false,
    val enableComparison: Boolean = false,
) {
    fun searchedBreeds(): List<BreedModel> {
        return breeds.filter { it.displayName.contains(searchValue, ignoreCase = true) }
    }

    fun isSelected(breed: BreedModel): Boolean = comparingBreeds.contains(breed)

    val allowToSelectBreed: Boolean
        get() = enableComparison && comparingBreeds.size <= 1
}
