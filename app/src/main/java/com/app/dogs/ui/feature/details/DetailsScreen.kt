package com.app.dogs.ui.feature.details

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.app.dogs.ui.feature.comparison.BreedViewParameterProvider
import com.app.dogs.ui.theme.AppTheme
import com.app.dogs.ui.theme.space2
import com.app.dogs.ui.theme.space4
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@VisibleForTesting
internal const val BREED_DATA = "breed"

internal class DetailsArgs(val breedData: BreedModel) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        Json.decodeFromString<BreedModel>(checkNotNull(savedStateHandle[BREED_DATA]))
    )
}

fun NavController.navigateToDetailsView(model: BreedModel) {
    val breedData = Json.encodeToString(model)
    navigate("${Screen.Detail.routeName}/$breedData")
}

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composable(
        route = "${Screen.Detail.routeName}/{$BREED_DATA}",
        arguments = listOf(
            navArgument(BREED_DATA) { type = NavType.StringType },
        ),
    ) {
        val viewModel: DetailsViewModel = hiltViewModel()
        val vmState by viewModel.uiState.collectAsStateWithLifecycle()
        DetailsView(vmState = vmState, onBackClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun DetailsView(
    @PreviewParameter(BreedViewParameterProvider::class) vmState: DetailsViewState,
    onBackClick: () -> Unit = {},
    actionFavorite: () -> Unit = {},
) {
    AppTheme {
        Column {
            TopAppBar(
                title = { TextHeadlineMediumPrimary(vmState.breedModel?.displayName ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                HorizontalPager(
                    state = rememberPagerState { vmState.breedImages.count() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                ) { i ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(space4)
                            .clip(RoundedCornerShape(space4))
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(space2),
                        model = vmState.breedImages[i],
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.ic_placeholder)
                    )
                }
            }
        }
    }
}


class BreedViewParameterProvider : PreviewParameterProvider<DetailsViewState> {
    override val values = sequenceOf(
        DetailsViewState(
            breedModel = BreedModel("Akati", "", true),
            breedImages = listOf(
                "https://images.dog.ceo/breeds/hound-ibizan/n02091244_2198.jpg",
                "https://images.dog.ceo/breeds/hound-walker/n02089867_1062.jpg",
                "https://images.dog.ceo/breeds/hound-walker/n02089867_2433.jpg"
            )
        )
    )
}
