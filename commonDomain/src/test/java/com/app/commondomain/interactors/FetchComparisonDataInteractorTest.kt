package com.app.commondomain.interactors

import com.app.commondomain.model.BreedModel
import com.app.commontest.BaseTest
import com.app.commontest.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchComparisonDataInteractorTest : BaseTest<FetchComparisonDataInteractor>() {

    override lateinit var classUnderTest: FetchComparisonDataInteractor

    private val fetchBreedImagesInteractor: FetchBreedImagesInteractor = mockk()

    private var breedModel = BreedModel(
        breed = "affenpinscher", subBreed = "", isFavorite = false
    )


    @Before
    override fun setUp() {
        classUnderTest = FetchComparisonDataInteractor(fetchBreedImagesInteractor)
    }

    /***
     * GIVEN FetchBreedImagesInteractor
     * WHEN i invoke interactor
     * THEN i expect ComparisonDataModel to be returned
     */
    @Test
    fun testRemoveFromFavorite() = runTest {
        val params = FetchComparisonDataInteractor.Params(breedModel, breedModel)
        test(
            given = {
                coEvery {
                    fetchBreedImagesInteractor.invoke(
                        FetchBreedImagesInteractor.Params(
                            params.firstBreedData.breed,
                            params.firstBreedData.subBreed,
                        )
                    )
                } returns listOf("image")
            },
            whenAction = {
                classUnderTest.invoke(params)
            },
            then = {
                assertEquals(listOf("image"), it.firstBreedImages)
                assertEquals(listOf("image"), it.secondBreedImages)
            }
        )
    }
}
