package com.app.dogs.data.repository

import com.app.commondata.api.ApiService
import com.app.commondata.dto.BreedImagesResponseDTO
import com.app.commondata.dto.BreedListResponseDTO
import com.app.commondata.repository.DogsRepositoryImpl
import com.app.commondata.room.BreedDao
import com.app.commondata.room.BreedEntity
import com.app.commondomain.model.BreedModel
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
import retrofit2.Response

@ExperimentalCoroutinesApi
class DogsRepositoryImplTest {

    private val breedDao: BreedDao = mockk()
    private val apiService: ApiService = mockk()
    private lateinit var repository: DogsRepositoryImpl

    private val breedModel = BreedModel(
        breed = "name",
        subBreed = "symbol",
        isFavorite = true
    )

    private val breedEntity = BreedEntity(breedModel)

    @Before
    fun setUp() {
        repository = DogsRepositoryImpl(breedDao, apiService)
    }

    /***
     * GIVEN apiService.fetchDogBreeds returns success response
     * WHEN i invoke fetchDogBreeds
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testFetchDogBreeds() = runTest {
        //given
        val responseDTO = BreedListResponseDTO(
            message = mapOf(
                Pair("akita", listOf(""))
            ),
            status = "Success"
        )

        val response = Response.success(responseDTO)
        coEvery { apiService.fetchDogBreeds() } returns response
        coEvery { breedDao.findWatchlist("akita") } returns breedEntity

        //when
        val result = repository.fetchDogBreeds()

        //then
        assertEquals(listOf(BreedModel(breed = "akita", subBreed = "", isFavorite = true)), result)
    }

    /***
     * GIVEN apiService.fetchDogBreedImages returns success response
     * WHEN i invoke fetchDogBreedImages
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testFetchDogBreedImages() = runTest {
        //given
        val responseDTO = BreedImagesResponseDTO(
            message = listOf("image"),
            status = "Success"
        )

        val response = Response.success(responseDTO)
        coEvery { apiService.fetchDogBreedImages("akita") } returns response

        //when
        val result = repository.fetchDogBreedImages("akita", "")

        //then
        assertEquals(listOf("image"), result)
    }

    /***
     * GIVEN apiService.fetchDogBreedImages returns success response
     * WHEN i invoke fetchDogBreedImages
     * THEN i expect list of BreedModel to be returned
     */
    @Test
    fun testFetchDogBreedImagesWithSubBreed() = runTest {
        //given
        val responseDTO = BreedImagesResponseDTO(
            message = listOf("image"),
            status = "Success"
        )

        val response = Response.success(responseDTO)
        coEvery { apiService.fetchDogSubBreedImages("akita", "test") } returns response

        //when
        val result = repository.fetchDogBreedImages("akita", "test")

        //then
        assertEquals(listOf("image"), result)
    }

    /***
     * GIVEN exerciseDao.getFavoriteBreeds return live data
     * WHEN i invoke getFavoriteBreeds
     * THEN i expect same live data to be returned
     */
    @Test
    fun testGetFavoriteBreed() = runTest {
        //given
        val liveData: Flow<List<BreedEntity>> = flow { emit(listOf(breedEntity)) }
        coEvery { breedDao.getFavoriteBreeds() } returns liveData

        //when
        val result = repository.getFavoriteBreed()

        //then
        assertEquals(listOf(breedModel), result.first())
    }

    /***
     * GIVEN breedDao.insert with BreedModel
     * WHEN i invoke saveToWatchlist
     * THEN i expect BreedModel to be saved
     */
    @Test
    fun testSaveToFavorite() = runTest {
        //given
        coEvery { breedDao.insert(breedEntity) } just runs

        //when
        repository.saveToFavorite(breedModel)

        //then
        coVerify { breedDao.insert(breedEntity) }
    }

    /***
     * GIVEN breedDao.delete with BreedModel
     * WHEN i invoke removeFromFavorite
     * THEN i expect BreedModel to be delete
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        //given
        coEvery { breedDao.delete(breedEntity) } just runs

        //when
        repository.removeFromFavorite(breedModel)

        //then
        coVerify { breedDao.delete(breedEntity) }
    }
}
