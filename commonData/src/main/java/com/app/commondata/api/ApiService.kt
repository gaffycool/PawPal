package com.app.commondata.api

import com.app.commondata.dto.BreedImagesResponseDTO
import com.app.commondata.dto.BreedListResponseDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Used to connect to the Unsplash API to fetch photos
 */
interface ApiService {

    @GET("api/breeds/list/all")
    suspend fun fetchDogBreeds(): Response<BreedListResponseDTO>

    @GET("api/breed/{breed}/images/random/10")
    suspend fun fetchDogBreedImages(@Path("breed") breed: String): Response<BreedImagesResponseDTO>

    @GET("api/breed/{breed}/{subBreed}/images/random/10")
    suspend fun fetchDogSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): Response<BreedImagesResponseDTO>

    companion object {
        const val BASE_URL = "https://dog.ceo"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
