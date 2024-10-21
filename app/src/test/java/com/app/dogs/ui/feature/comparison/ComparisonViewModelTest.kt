package com.app.dogs.ui.feature.comparison

import androidx.lifecycle.SavedStateHandle
import com.app.commondomain.interactors.ComparisonDataModel
import com.app.commondomain.interactors.FetchComparisonDataInteractor
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
class ComparisonViewModelTest {

    private val fetchComparisonDataInteractor: FetchComparisonDataInteractor = mockk()

    private lateinit var viewModel: ComparisonViewModel

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
            fetchComparisonDataInteractor.get(
                FetchComparisonDataInteractor.Params(breedModel, breedModel)
            )
        } returns ComparisonDataModel(emptyList(), emptyList())
        viewModel = ComparisonViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    FIRST_BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                    SECOND_BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                )
            ),
            fetchComparisonDataInteractor
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
        viewModel.fetchBreedDetails(breedModel, breedModel)
        advanceUntilIdle()

        //then
        assertEquals(breedModel, viewModel.uiState.value.firstBreedData.breedModel)
        assertEquals(breedModel, viewModel.uiState.value.secondBreedData.breedModel)
    }

}