package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchComparisonDataInteractorTest {

    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor = mockk()
    private lateinit var fetchComparisonDataInteractor: FetchComparisonDataInteractor

    private var breedModel = BreedModel(
        breed = "affenpinscher", subBreed = "", isFavorite = false
    )


    @Before
    fun setUp() {
        fetchComparisonDataInteractor = FetchComparisonDataInteractor(fetchBreedImagesInteractor)
    }

    /***
     * GIVEN FetchBreedImagesInteractor
     * WHEN i invoke interactor
     * THEN i expect ComparisonDataModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        val params = FetchComparisonDataInteractor.Params(breedModel, breedModel)
        //given
        coEvery {
            fetchBreedImagesInteractor.get(
                FetchBreedImagesInteractor.Params(
                    params.firstBreedData.breed,
                    params.firstBreedData.subBreed,
                )
            )
        } returns listOf("image")

        //when
        val result = fetchComparisonDataInteractor.get(params)
        advanceUntilIdle()

        //then
        assertEquals(listOf("image"), result.firstBreedImages)
        assertEquals(listOf("image"), result.secondBreedImages)
    }
}
