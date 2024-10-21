package com.app.dogs.ui.feature.favorite

import com.app.commondomain.interactors.GetFavoriteBreedInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    private val getFavoriteBreedInteractor: GetFavoriteBreedInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()
    private lateinit var viewModel: FavoriteViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val breedModel = BreedModel(
        breed = "name",
        subBreed = "symbol",
        isFavorite = true
    )
    private val listLiveData: Flow<List<BreedModel>> =
        flow { listOf(breedModel) }

    @Before
    fun setUp() {
        coEvery { getFavoriteBreedInteractor.get() } returns listLiveData
        viewModel = FavoriteViewModel(
            getFavoriteBreedInteractor,
            removeFromFavoriteInteractor
        )
    }

    @Test
    fun testInit() = runTest {
        //then
        val expectedState = viewModel.uiState.value
        assertEquals(FavoriteViewState.Content(listLiveData), expectedState)
    }

    @Test
    fun testActionFavorite() = runTest {
        //when
        val breedModel: BreedModel = mockk()
        coJustRun { removeFromFavoriteInteractor.remove(breedModel) }

        viewModel.actionFavorite(breedModel)
        advanceUntilIdle()

        //then
        coVerify { removeFromFavoriteInteractor.remove(breedModel) }
    }
}
