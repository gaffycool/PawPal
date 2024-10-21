package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoriteBreedInteractorTest {

    private val dogsRepository: DogsRepository = mockk()
    private lateinit var getFavoriteBreedInteractor: GetFavoriteBreedInteractor

    @Before
    fun setUp() {
        getFavoriteBreedInteractor = GetFavoriteBreedInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.getFavoriteBreed
     * WHEN i invoke interactor
     * THEN i expect flow of BreedModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        val flow = flow<List<BreedModel>> { }
        //given
        coEvery { dogsRepository.getFavoriteBreed() } returns flow

        //when
        getFavoriteBreedInteractor.get()

        //then
        coVerify { dogsRepository.getFavoriteBreed() }
    }
}