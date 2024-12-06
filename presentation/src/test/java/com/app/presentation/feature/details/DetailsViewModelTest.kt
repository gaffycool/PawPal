package com.app.presentation.feature.details

import androidx.lifecycle.SavedStateHandle
import com.app.commondomain.interactors.FetchBreedImagesInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class DetailsViewModelTest : BaseViewModelTest<DetailsViewModel>() {

    override lateinit var classUnderTest: DetailsViewModel

    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor = mockk()

    private var breedModel = BreedModel(
        breed = "affenpinscher",
        subBreed = "",
        isFavorite = false
    )

    @Before
    override fun setUp() {
        super.setUp()
        coEvery {
            fetchBreedImagesInteractor.invoke(
                FetchBreedImagesInteractor.Params(
                    breedModel.breed,
                    breedModel.subBreed
                )
            )
        } returns listOf("image")
        classUnderTest = DetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                )
            ),
            fetchBreedImagesInteractor,
            interactorRunner
        )
    }

    /***
     * GIVEN fetchComparisonDataInteractor return list of breedModel
     * WHEN i invoke fetchBreedDetails
     * THEN i expect viewstate should updated with same
     */
    @Test
    fun testFetchBreedDetails() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.fetchBreedDetails(breedModel)
                advanceUntilIdle()
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(listOf("image"), it.breedImages)
            }
        )
    }
}