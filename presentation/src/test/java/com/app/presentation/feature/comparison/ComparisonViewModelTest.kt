package com.app.presentation.feature.comparison

import androidx.lifecycle.SavedStateHandle
import com.app.commondomain.interactors.ComparisonDataModel
import com.app.commondomain.interactors.FetchComparisonDataInteractor
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
class ComparisonViewModelTest : BaseViewModelTest<ComparisonViewModel>() {

    override lateinit var classUnderTest: ComparisonViewModel

    private val fetchComparisonDataInteractor: FetchComparisonDataInteractor = mockk()

    private var breedModel = BreedModel(
        breed = "affenpinscher",
        subBreed = "",
        isFavorite = false
    )

    @Before
    override fun setUp() {
        super.setUp()
        coEvery {
            fetchComparisonDataInteractor.invoke(
                FetchComparisonDataInteractor.Params(breedModel, breedModel)
            )
        } returns ComparisonDataModel(emptyList(), emptyList())
        classUnderTest = ComparisonViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    FIRST_BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                    SECOND_BREED_DATA to "{\"breed\":\"affenpinscher\",\"subBreed\":\"\",\"isFavorite\":false}",
                )
            ),
            fetchComparisonDataInteractor,
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
                classUnderTest.fetchBreedDetails(breedModel, breedModel)
                advanceUntilIdle()
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(breedModel, it.firstBreedData.breedModel)
                assertEquals(breedModel, it.secondBreedData.breedModel)
            }
        )
    }
}