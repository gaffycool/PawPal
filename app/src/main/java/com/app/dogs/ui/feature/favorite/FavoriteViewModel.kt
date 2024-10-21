package com.app.dogs.ui.feature.favorite

import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.GetFavoriteBreedInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject internal constructor(
    getFavoriteBreedInteractor: GetFavoriteBreedInteractor,
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor,
) : BaseViewModel<FavoriteViewState>() {

    override fun defaultState(): FavoriteViewState = FavoriteViewState.Empty

    init {
        viewModelScope.launch {
            val favouriteBreed = getFavoriteBreedInteractor.get()
            newState { FavoriteViewState.Content(favouriteBreed) }
        }
    }

    fun actionFavorite(breedModel: BreedModel) {
        viewModelScope.launch {
            removeFromFavoriteInteractor.remove(breedModel)
        }
    }
}

sealed class FavoriteViewState {
    data object Empty : FavoriteViewState()
    data class Content(val breed: Flow<List<BreedModel>>) : FavoriteViewState()
}
