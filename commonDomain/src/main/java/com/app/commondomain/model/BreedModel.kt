package com.app.commondomain.model

import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class BreedModel(
    val breed: String,
    val subBreed: String,
    val isFavorite: Boolean
) {
    val symbol = "$breed$subBreed"

    val displayName = "$breed $subBreed".replaceFirstChar { char ->
        if (char.isLowerCase()) char.titlecase(Locale.getDefault())
        else char.toString()
    }
}