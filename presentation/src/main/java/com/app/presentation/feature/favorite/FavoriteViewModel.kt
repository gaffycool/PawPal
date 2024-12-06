package com.app.presentation.feature.favorite

import androidx.lifecycle.viewModelScope
import com.app.commondomain.interactors.GetFavoriteBreedInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.base.BaseViewModel
import com.app.presentation.base.InteractorRunner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject internal constructor(
    getFavoriteBreedInteractor: GetFavoriteBreedInteractor,
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor,
    private val interactorRunner: InteractorRunner,
) : BaseViewModel<FavoriteViewState>() {

    override fun defaultState(): FavoriteViewState = FavoriteViewState.Empty

    init {
        interactorRunner(
            job = viewModelScope,
            action = { getFavoriteBreedInteractor.invoke() },
            onSuccess = { favouriteBreed ->
                newState { FavoriteViewState.Content(favouriteBreed) }
            }
        )
    }

    fun actionFavorite(breedModel: BreedModel) {
        interactorRunner(
            job = viewModelScope,
            action = { removeFromFavoriteInteractor.invoke(breedModel) },
            onSuccess = {}
        )
    }
}

sealed class FavoriteViewState {
    data object Empty : FavoriteViewState()
    data class Content(val breed: Flow<List<BreedModel>>) : FavoriteViewState()
}
