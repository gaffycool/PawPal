package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commondomain.repository.DogsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchDogBreedsInteractorTest {

    private val dogsRepository: DogsRepository = mockk()
    private lateinit var fetchDogBreedsInteractor: FetchDogBreedsInteractor

    @Before
    fun setUp() {
        fetchDogBreedsInteractor = FetchDogBreedsInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.fetchDogBreeds
     * WHEN i invoke interactor
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testFetchDogBreeds() = runTest {
        val breedModels: List<BreedModel> = mockk()
        //given
        coEvery { dogsRepository.fetchDogBreeds() } returns breedModels

        //when
        fetchDogBreedsInteractor.get()

        //then
        coVerify { dogsRepository.fetchDogBreeds() }
    }
}
