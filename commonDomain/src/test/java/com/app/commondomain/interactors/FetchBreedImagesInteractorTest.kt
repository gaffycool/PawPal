package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchBreedImagesInteractorTest {

    private val dogsRepository: DogsRepository = mockk()
    private lateinit var fetchBreedImagesInteractor: FetchBreedImagesInteractor

    @Before
    fun setUp() {
        fetchBreedImagesInteractor = FetchBreedImagesInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.fetchDogBreedImages
     * WHEN i invoke interactor
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        val params = FetchBreedImagesInteractor.Params("b", "sub")
        //given
        coEvery {
            dogsRepository.fetchDogBreedImages("b", "sub")
        } returns listOf("image")

        //when
        fetchBreedImagesInteractor.get(params)

        //then
        coVerify { dogsRepository.fetchDogBreedImages("b", "sub") }
    }
}
