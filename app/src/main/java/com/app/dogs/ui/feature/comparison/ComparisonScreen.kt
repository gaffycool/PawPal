package com.app.dogs.ui.feature.comparison

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.app.commondomain.model.BreedModel
import com.app.dogs.R
import com.app.dogs.ui.Screen
import com.app.dogs.ui.component.text.TextHeadlineMediumPrimary
import com.app.dogs.ui.feature.details.BreedViewParameterProvider
import com.app.dogs.ui.theme.AppTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@VisibleForTesting
internal const val FIRST_BREED_DATA = "first_breed"
internal const val SECOND_BREED_DATA = "second_breed"

internal class ComparisonArgs(val firstBreedData: BreedModel, val secondBreedData: BreedModel) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        Json.decodeFromString<BreedModel>(checkNotNull(savedStateHandle[FIRST_BREED_DATA])),
        Json.decodeFromString<BreedModel>(checkNotNull(savedStateHandle[SECOND_BREED_DATA])),
    )
}

fun NavController.navigateToComparisonView(
    firstBreedData: BreedModel, secondBreedData: BreedModel
) {
    navigate(
        Screen.Detail.routeName +
                "/${Json.encodeToString(firstBreedData)}" +
                "/${Json.encodeToString(secondBreedData)}"
    )
}

fun NavGraphBuilder.comparisonScreen(onBackClick: () -> Unit) {
    composable(
        route = "${Screen.Detail.routeName}/{$FIRST_BREED_DATA}/{$SECOND_BREED_DATA}",
        arguments = listOf(
            navArgument(FIRST_BREED_DATA) { type = NavType.StringType },
            navArgument(SECOND_BREED_DATA) { type = NavType.StringType },
        ),
    ) {
        val viewModel: ComparisonViewModel = hiltViewModel()
        val vmState by viewModel.uiState.collectAsStateWithLifecycle()
        ComparisonView(
            vmState = vmState,
            onBackClick = onBackClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun ComparisonView(
    @PreviewParameter(BreedViewParameterProvider::class) vmState: ComparisonViewState,
    onBackClick: () -> Unit = {},
    actionFavorite: () -> Unit = {},
) {
    AppTheme {
        Column {
            TopAppBar(
                title = { TextHeadlineMediumPrimary("Comparing") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    minOf(
                        vmState.firstBreedData.breedImages.size,
                        vmState.secondBreedData.breedImages.size
                    )
                ) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AsyncImage(
                            modifier = Modifier.weight(1f),
                            model = vmState.firstBreedData.breedImages[index],
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.ic_placeholder)
                        )
                        AsyncImage(
                            modifier = Modifier.weight(1f),
                            model = vmState.secondBreedData.breedImages[index],
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.ic_placeholder)
                        )
                    }
                }
            }
        }
    }
}


class BreedViewParameterProvider : PreviewParameterProvider<ComparisonViewState> {
    override val values = sequenceOf(
        ComparisonViewState(

        )
    )
}
