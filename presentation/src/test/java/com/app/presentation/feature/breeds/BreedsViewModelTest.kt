package com.app.presentation.feature.breeds

import com.app.commondomain.interactors.FetchDogBreedsInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.interactors.SaveToFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.presentation.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
internal class BreedsViewModelTest : BaseViewModelTest<BreedsViewModel>() {

    override lateinit var classUnderTest: BreedsViewModel

    private val fetchDogBreedsInteractor: FetchDogBreedsInteractor = mockk()
    private val saveToFavoriteInteractor: SaveToFavoriteInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()

    private var breedModel = BreedModel(
        breed = "name",
        subBreed = "symbol",
        isFavorite = true
    )

    @Before
    override fun setUp() {
        super.setUp()
        coEvery { fetchDogBreedsInteractor.invoke() } returns emptyList()
        classUnderTest = BreedsViewModel(
            fetchDogBreedsInteractor,
            saveToFavoriteInteractor,
            removeFromFavoriteInteractor,
            interactorRunner
        )
    }

    /***
     * GIVEN fetchDogBreedsInteractor return list of BreedModel
     * WHEN i invoke fetchBreeds
     * THEN i expect viewstate should updated with same list of BreedModel
     */
    @Test
    fun testFetchBreeds() = runTest {
        val breedModels: List<BreedModel> = listOf(breedModel)
        testViewState(
            given = {
                coEvery { fetchDogBreedsInteractor.invoke() } returns breedModels
            },
            whenAction = {
                classUnderTest.fetchBreeds()
                advanceUntilIdle()
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(breedModels, it.breeds)
            }
        )
    }

    /***
     * GIVEN removeFromFavoriteInteractor with favorite true
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionRemoveFromFavorite() = runTest {
        testViewState(
            given = {
                testFetchBreeds()
                coJustRun { removeFromFavoriteInteractor.invoke(breedModel) }
            },
            whenAction = {
                classUnderTest.actionFavorite(0)
                advanceUntilIdle()
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(
                    breedModel.copy(isFavorite = false),
                    it.breeds.first()
                )
            }
        )
    }

    /***
     * GIVEN saveToFavoriteInteractor with favorite false
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionSaveToFavorite() = runTest {
        testViewState(
            given = {
                breedModel = breedModel.copy(isFavorite = false)
                testFetchBreeds()
                val expected = breedModel.copy(isFavorite = true)
                coJustRun { saveToFavoriteInteractor.invoke(expected) }
            },
            whenAction = {
                classUnderTest.actionFavorite(0)
                advanceUntilIdle()
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(breedModel.copy(isFavorite = true), it.breeds.first())
            }
        )
    }

    /***
     * GIVEN value
     * WHEN i invoke onValueChange
     * THEN i expect state has been updated
     */
    @Test
    fun testOnValueChange() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.onValueChange("value")
                classUnderTest.uiState.value
            },
            then = {
                assertEquals("value", it.searchValue)
            }
        )
    }

    /***
     * GIVEN value
     * WHEN i invoke onValueChange
     * THEN i expect state has been updated
     */
    @Test
    fun testOnValueChangeEmptyValue() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.onValueChange("")
                classUnderTest.uiState.value
            },
            then = {
                assertEquals("", it.searchValue)
                assertFalse(it.showDropdownMenu)
            }
        )
    }

    /***
     * WHEN i invoke onDismissMenu
     * THEN i expect state has been updated
     */
    @Test
    fun testDismissMenu() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.onDismissMenu()
                classUnderTest.uiState.value
            },
            then = {
                assertFalse(it.showDropdownMenu)
            }
        )
    }


    /***
     * WHEN i invoke showSearchMenu
     * THEN i expect state has been updated
     */
    @Test
    fun testShowSearchMenu() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.showSearchMenu(true)
                classUnderTest.uiState.value
            },
            then = {
                assertTrue(it.showSearchBar)
            }
        )
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testEnableComparison() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.enableComparison()
                classUnderTest.uiState.value
            },
            then = {
                assertTrue(it.enableComparison)
            }
        )
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testEnableComparisonWhenItsTrue() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.enableComparison()
                classUnderTest.enableComparison()
                classUnderTest.uiState.value
            },
            then = {
                assertFalse(it.enableComparison)
            }
        )
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testSelectBreed() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.enableComparison()
                classUnderTest.selectBreed(breedModel)
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(listOf(breedModel), it.comparingBreeds)
            }
        )
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testDeSelectBreed() = runTest {
        testViewState(
            whenAction = {
                testSelectBreed()
                classUnderTest.selectBreed(breedModel)
                classUnderTest.uiState.value
            },
            then = {
                assertTrue(it.comparingBreeds.isEmpty())
            }
        )
    }
}
