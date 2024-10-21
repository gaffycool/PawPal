package com.app.commondata.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedImagesResponseDTO(
    val message: List<String>,
    val status: String
)