package com.app.commondomain.interactors

import com.app.commondomain.repository.DogsRepository
import com.app.commontest.BaseTest
import com.app.commontest.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoriteBreedInteractorTest : BaseTest<GetFavoriteBreedInteractor>() {

    override lateinit var classUnderTest: GetFavoriteBreedInteractor

    private val dogsRepository: DogsRepository = mockk()

    @Before
    override fun setUp() {
        classUnderTest = GetFavoriteBreedInteractor(dogsRepository)
    }

    /***
     * GIVEN dogsRepository.getFavoriteBreed
     * WHEN i invoke interactor
     * THEN i expect flow of BreedModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        test(
            given = {
                coEvery { dogsRepository.getFavoriteBreed() } returns flow { }
            },
            whenAction = { classUnderTest.invoke() },
            then = {
                coVerify { dogsRepository.getFavoriteBreed() }
            }
        )
    }
}