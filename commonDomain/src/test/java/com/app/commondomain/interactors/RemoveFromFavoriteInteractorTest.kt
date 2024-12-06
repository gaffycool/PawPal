package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import com.app.commontest.BaseTest
import com.app.commontest.test
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoveFromFavoriteInteractorTest : BaseTest<RemoveFromFavoriteInteractor>() {

    override lateinit var classUnderTest: RemoveFromFavoriteInteractor

    private val dogsRepository: DogsRepository = mockk()

    @Before
    override fun setUp() {
        classUnderTest = RemoveFromFavoriteInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.removeFromWatchlist
     * WHEN i invoke interactor
     * THEN i expect it execute successfully
     */
    @Test
    fun testRemoveFromWatchlist() = runTest {
        val model: BreedModel = mockk()
        test(
            given = {
                coJustRun { dogsRepository.removeFromFavorite(model) }
            },
            whenAction = { classUnderTest.invoke(model) },
            then = {
                coVerify { dogsRepository.removeFromFavorite(model) }
            }
        )
    }
}
