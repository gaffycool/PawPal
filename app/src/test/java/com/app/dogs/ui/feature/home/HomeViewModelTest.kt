package com.app.dogs.ui.feature.home

import com.app.dogs.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    /***
     * WHEN i invoke onPageChange
     * THEN i expect viewstate should updated with same
     */
    @Test
    fun testOnPageChange() = runTest {
        //given
        viewModel.onPageChange(1)

        //then
        assertEquals(1, viewModel.uiState.value.selectedPage)
    }

}