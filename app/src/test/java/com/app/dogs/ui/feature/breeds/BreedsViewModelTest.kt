package com.app.dogs.ui.feature.breeds

import com.app.commondomain.interactors.FetchDogBreedsInteractor
import com.app.commondomain.interactors.RemoveFromFavoriteInteractor
import com.app.commondomain.interactors.SaveToFavoriteInteractor
import com.app.commondomain.model.BreedModel
import com.app.dogs.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
class BreedsViewModelTest {

    private val fetchDogBreedsInteractor: FetchDogBreedsInteractor = mockk()
    private val saveToFavoriteInteractor: SaveToFavoriteInteractor = mockk()
    private val removeFromFavoriteInteractor: RemoveFromFavoriteInteractor = mockk()

    private lateinit var viewModel: BreedsViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var breedModel = BreedModel(
        breed = "name",
        subBreed = "symbol",
        isFavorite = true
    )

    @Before
    fun setUp() {
        coEvery { fetchDogBreedsInteractor.get() } returns emptyList()
        viewModel = BreedsViewModel(
            fetchDogBreedsInteractor,
            saveToFavoriteInteractor,
            removeFromFavoriteInteractor
        )
    }

    /***
     * GIVEN fetchDogBreedsInteractor return list of BreedModel
     * WHEN i invoke fetchBreeds
     * THEN i expect viewstate should updated with same list of BreedModel
     */
    @Test
    fun testFetchBreeds() = runTest {
        //given
        val breedModels: List<BreedModel> = listOf(breedModel)
        coEvery { fetchDogBreedsInteractor.get() } returns breedModels

        viewModel.fetchBreeds()
        advanceUntilIdle()

        //then
        assertEquals(breedModels, viewModel.uiState.value.breeds)
    }

    /***
     * GIVEN removeFromFavoriteInteractor with favorite true
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionRemoveFromFavorite() = runTest {
        //given
        testFetchBreeds()

        coJustRun { removeFromFavoriteInteractor.remove(breedModel) }

        //when
        viewModel.actionFavorite(0)
        advanceUntilIdle()

        //then
        assertEquals(
            breedModel.copy(isFavorite = false),
            viewModel.uiState.value.breeds.first()
        )
    }

    /***
     * GIVEN saveToFavoriteInteractor with favorite false
     * WHEN i invoke actionFavorite
     * THEN i expect viewstate should updated with favorite value
     */
    @Test
    fun testActionSaveToFavorite() = runTest {
        //given
        breedModel = breedModel.copy(isFavorite = false)
        testFetchBreeds()
        val expected = breedModel.copy(isFavorite = true)
        coJustRun { saveToFavoriteInteractor.save(expected) }

        //when
        viewModel.actionFavorite(0)
        advanceUntilIdle()

        //then
        assertEquals(
            expected,
            viewModel.uiState.value.breeds.first()
        )
    }

    /***
     * GIVEN value
     * WHEN i invoke onValueChange
     * THEN i expect state has been updated
     */
    @Test
    fun testOnValueChange() = runTest {
        //when
        viewModel.onValueChange("value")

        //then
        assertEquals(
            "value",
            viewModel.uiState.value.searchValue
        )
    }

    /***
     * GIVEN value
     * WHEN i invoke onValueChange
     * THEN i expect state has been updated
     */
    @Test
    fun testOnValueChangeEmptyValue() = runTest {
        //when
        viewModel.onValueChange("")

        //then
        assertEquals(
            "",
            viewModel.uiState.value.searchValue
        )
        assertFalse(viewModel.uiState.value.showDropdownMenu)
    }

    /***
     * WHEN i invoke onDismissMenu
     * THEN i expect state has been updated
     */
    @Test
    fun testDismissMenu() = runTest {
        //when
        viewModel.onDismissMenu()

        //then
        assertFalse(viewModel.uiState.value.showDropdownMenu)
    }


    /***
     * WHEN i invoke showSearchMenu
     * THEN i expect state has been updated
     */
    @Test
    fun testShowSearchMenu() = runTest {
        //when
        viewModel.showSearchMenu(true)

        //then
        assertTrue(viewModel.uiState.value.showSearchBar)
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testEnableComparison() = runTest {
        //when
        viewModel.enableComparison()

        //then
        assertTrue(viewModel.uiState.value.enableComparison)
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testEnableComparisonWhenItsTrue() = runTest {
        //when
        viewModel.enableComparison()
        viewModel.enableComparison()

        //then
        assertFalse(viewModel.uiState.value.enableComparison)
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testSelectBreed() = runTest {
        //when
        viewModel.enableComparison()
        viewModel.selectBreed(breedModel)

        //then
        assertEquals(listOf(breedModel), viewModel.uiState.value.comparingBreeds)
    }

    /***
     * WHEN i invoke enableComparison
     * THEN i expect state has been updated
     */
    @Test
    fun testDeSelectBreed() = runTest {
        //when
        testSelectBreed()
        viewModel.selectBreed(breedModel)

        //then
        assertTrue(viewModel.uiState.value.comparingBreeds.isEmpty())
    }
}
