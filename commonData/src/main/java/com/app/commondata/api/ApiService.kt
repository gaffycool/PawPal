package com.app.commondata.api

import com.app.commondata.dto.BreedImagesResponseDTO
import com.app.commondata.dto.BreedListResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Used to connect to the Dog to fetch breeds details and images
 */
interface ApiService {

    @GET("api/breeds/list/all")
    suspend fun fetchDogBreeds(): BreedListResponseDTO

    @GET("api/breed/{breed}/images/random/10")
    suspend fun fetchDogBreedImages(@Path("breed") breed: String): BreedImagesResponseDTO

    @GET("api/breed/{breed}/{subBreed}/images/random/10")
    suspend fun fetchDogSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): BreedImagesResponseDTO
}
