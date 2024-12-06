package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import com.app.commontest.BaseTest
import com.app.commontest.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchDogBreedsInteractorTest : BaseTest<FetchDogBreedsInteractor>() {

    override lateinit var classUnderTest: FetchDogBreedsInteractor

    private val dogsRepository: DogsRepository = mockk()

    @Before
    override fun setUp() {
        classUnderTest = FetchDogBreedsInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.fetchDogBreeds
     * WHEN i invoke interactor
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testFetchDogBreeds() = runTest {
        test(
            given = {
                coEvery { dogsRepository.fetchDogBreeds() } returns Result.success(mockk())
            },
            whenAction = { classUnderTest.invoke() },
            then = {
                coVerify { dogsRepository.fetchDogBreeds() }
            }
        )
    }
}
