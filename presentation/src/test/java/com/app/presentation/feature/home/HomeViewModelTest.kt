package com.app.presentation.feature.home

import com.app.presentation.BaseViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseViewModelTest<HomeViewModel>() {

    override lateinit var classUnderTest: HomeViewModel

    @Before
    override fun setUp() {
        super.setUp()
        classUnderTest = HomeViewModel()
    }

    /***
     * WHEN i invoke onPageChange
     * THEN i expect viewstate should updated with same
     */
    @Test
    fun testOnPageChange() = runTest {
        testViewState(
            whenAction = {
                classUnderTest.onPageChange(1)
                classUnderTest.uiState.value
            },
            then = {
                assertEquals(1, it.selectedPage)
            }
        )
    }
}