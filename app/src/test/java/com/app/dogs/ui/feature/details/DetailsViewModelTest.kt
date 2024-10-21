package com.app.dogs.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import com.app.commondomain.interactors.FetchBreedImagesInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor = mockk()

    private lateinit var viewModel: DetailsViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var breedModel = BreedModel(
        breed = "affenpinscher",
        subBreed = "",
        isFavorite = false
    )

    @Before
    fun setUp() {
        coEvery {
            fetchBreedImagesInteractor.get(
                FetchBreedImagesInteractor.Params(
                    breedModel.breed,
                    breedModel.subBreed
                )
            )
        } returns listOf("image")
        viewModel = DetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                )
            ),
            fetchBreedImagesInteractor
        )
    }

    /***
     * GIVEN fetchComparisonDataInteractor return list of breedModel
     * WHEN i invoke fetchBreedDetails
     * THEN i expect viewstate should updated with same
     */
    @Test
    fun testFetchBreedDetails() = runTest {
        //given
        viewModel.fetchBreedDetails(breedModel)
        advanceUntilIdle()

        //then
        assertEquals(listOf("image"), viewModel.uiState.value.breedImages)
    }

}