package com.app.commondata.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedListResponseDTO(
    val message: Map<String, List<String>>,
    val status: String
)