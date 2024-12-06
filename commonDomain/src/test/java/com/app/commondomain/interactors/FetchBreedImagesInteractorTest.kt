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
class FetchBreedImagesInteractorTest : BaseTest<FetchBreedImagesInteractor>() {

    override lateinit var classUnderTest: FetchBreedImagesInteractor
    private val dogsRepository: DogsRepository = mockk()

    @Before
    override fun setUp() {
        classUnderTest = FetchBreedImagesInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.fetchDogBreedImages
     * WHEN i invoke interactor
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        test(
            given = {
                coEvery {
                    dogsRepository.fetchDogBreedImages("b", "sub")
                } returns Result.success(listOf("image"))
            },
            whenAction = {
                classUnderTest.invoke(
                    FetchBreedImagesInteractor.Params("b", "sub")
                )
            },
            then = {
                coVerify { dogsRepository.fetchDogBreedImages("b", "sub") }
            }
        )
    }
}
