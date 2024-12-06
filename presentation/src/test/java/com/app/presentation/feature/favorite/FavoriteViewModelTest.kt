package com.app.presentation.feature.favorite

import com.app.commondomain.interactors.GetFavoriteBreedInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.BaseViewModelTest
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
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class FavoriteViewModelTest : BaseViewModelTest<FavoriteViewModel>() {

    override lateinit var classUnderTest: FavoriteViewModel

    private val getFavoriteBreedInteractor: GetFavoriteBreedInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()

    private val breedModel = BreedModel(
        breed = "name",
        subBreed = "symbol",
        isFavorite = true
    )
    private val listLiveData: Flow<List<BreedModel>> =
        flow { listOf(breedModel) }

    @Before
    override fun setUp() {
        super.setUp()
        coEvery { getFavoriteBreedInteractor.invoke() } returns listLiveData
        classUnderTest = FavoriteViewModel(
            getFavoriteBreedInteractor,
            removeFromFavoriteInteractor,
            interactorRunner
        )
    }

    @Test
    fun testInit() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(FavoriteViewState.Content(listLiveData), it)
            }
        )
    }

    @Test
    fun testActionFavorite() = runTest {
        val breedModel: BreedModel = mockk()
        testViewState(
            given = {
                coJustRun { removeFromFavoriteInteractor.invoke(breedModel) }
            },
            whenAction = {
                classUnderTest.actionFavorite(breedModel)
                advanceUntilIdle()
            },
            then = {
                coVerify { removeFromFavoriteInteractor.invoke(breedModel) }
            }
        )
    }
}
