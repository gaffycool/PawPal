package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoveFromFavoriteInteractorTest {

    private val dogsRepository: DogsRepository = mockk()
    private lateinit var removeFromFavoriteInteractor: RemoveFromFavoriteInteractor

    @Before
    fun setUp() {
        removeFromFavoriteInteractor = RemoveFromFavoriteInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.removeFromWatchlist
     * WHEN i invoke interactor
     * THEN i expect it execute successfully
     */
    @Test
    fun testRemoveFromWatchlist() = runTest {
        val model: BreedModel = mockk()
        //given
        coJustRun { dogsRepository.removeFromFavorite(model) }

        //when
        removeFromFavoriteInteractor.remove(model)

        //then
        coVerify { dogsRepository.removeFromFavorite(model) }
    }
}
