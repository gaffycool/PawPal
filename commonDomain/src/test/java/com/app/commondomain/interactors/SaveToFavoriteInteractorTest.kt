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
class SaveToFavoriteInteractorTest {

    private val dogsRepository: DogsRepository = mockk()
    private lateinit var saveToFavoriteInteractor: SaveToFavoriteInteractor

    @Before
    fun setUp() {
        saveToFavoriteInteractor = SaveToFavoriteInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.saveToWatchlist
     * WHEN i invoke interactor
     * THEN i expect it execute successfully
     */
    @Test
    fun testSaveToWatchlist() = runTest {
        val model: BreedModel = mockk()
        //given
        coJustRun { dogsRepository.saveToFavorite(model) }

        //when
        saveToFavoriteInteractor.save(model)

        //then
        coVerify { dogsRepository.saveToFavorite(model) }
    }
}
