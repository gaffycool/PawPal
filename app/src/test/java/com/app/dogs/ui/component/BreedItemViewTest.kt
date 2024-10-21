package com.app.dogs.ui.component

import app.cash.paparazzi.Paparazzi
import com.app.commondomain.model.BreedModel
import org.junit.Rule
import org.junit.Test

class BreedItemViewTest {
    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun compose() {
        paparazzi.snapshot {
            BreedItemView(
                model = BreedModel(
                    subBreed = "sdf",
                    breed = "sdf",
                    isFavorite = true
                ),
                actionFavorite = {},
                onClick = {},
                isSelected = true
            )
        }
    }
}