package com.app.commondata.repository

import com.app.commondata.api.ApiService
import com.app.commondata.dto.BreedListResponseDTO
import com.app.commondata.mapper.DogBreedsResponseMapper
import com.app.commondata.room.BreedDao
import com.app.commondata.room.BreedEntity
import com.app.commondomain.model.BreedModel
import com.app.commontest.BaseTest
import com.app.commontest.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@ExperimentalCoroutinesApi
class DogsRepositoryImplTest : BaseTest<DogsRepositoryImpl>() {

    override lateinit var classUnderTest: DogsRepositoryImpl

    private val breedDao: BreedDao = mockk()
    private val apiService: ApiService = mockk()
    private val mapper: DogBreedsResponseMapper = mockk()

    private val breedEntity = BreedEntity(
        breed = "breed",
        subBreed = "subBreed",
        symbol = "breedsubBreed",
        isFavorite = true
    )

    private val breedModel = BreedModel(
        breed = "breed",
        subBreed = "subBreed",
        isFavorite = true
    )

    @Before
    override fun setUp() {
        this.classUnderTest = DogsRepositoryImpl(breedDao, apiService, mapper)
    }

    /***
     * GIVEN apiService.fetchDogBreeds returns success response
     * WHEN i invoke fetchBreeds
     * THEN i expect list of StockModel to be returned
     */
    @Test
    fun testFetchDogBreeds() = runTest {
        test(
            given = {
                val breedListResponseDTO = BreedListResponseDTO(
                    message = mapOf(
                        "breed" to listOf("breed")
                    ),
                    status = "done"
                )

                coEvery { apiService.fetchDogBreeds() } returns breedListResponseDTO
                coEvery { mapper.map(breedListResponseDTO.message) } returns listOf(breedModel)
            },
            whenAction = { classUnderTest.fetchDogBreeds() },
            then = {
                assertEquals(Result.success(listOf(breedModel)), it)
            }
        )
    }

    /***
     * GIVEN exerciseDao.getFavoriteBreeds return live data
     * WHEN i invoke getFavoriteBreeds
     * THEN i expect same live data to be returned
     */
    @Test
    fun testGetFavoriteBreeds() = runTest {
        val liveData: Flow<List<BreedEntity>> = flow { emit(listOf(breedEntity)) }
        test(
            given = {
                coEvery { breedDao.getFavoriteBreeds() } returns liveData
            },
            whenAction = {
                classUnderTest.getFavoriteBreed()
            },
            then = {
                assertEquals(listOf(breedModel), it.first())
            }
        )
    }

    /***
     * GIVEN stocksDao.insert with BreedModel
     * WHEN i invoke saveToWatchlist
     * THEN i expect StockModel to be saved
     */
    @Test
    fun testSaveToFavorite() = runTest {
        test(
            given = {
                coEvery { breedDao.insert(breedEntity) } just runs
            },
            whenAction = { classUnderTest.saveToFavorite(breedModel) },
            then = {
                coVerify { breedDao.insert(breedEntity) }
            }
        )
    }

    /***
     * GIVEN stocksDao.delete with StockModel
     * WHEN i invoke removeFromFavorite
     * THEN i expect StockModel to be delete
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        test(
            given = {
                coEvery { breedDao.delete(breedEntity) } just runs
            },
            whenAction = { classUnderTest.removeFromFavorite(breedModel) },
            then = {
                coVerify { breedDao.delete(breedEntity) }
            }
        )
    }
}