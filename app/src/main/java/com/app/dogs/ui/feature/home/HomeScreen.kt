package com.app.dogs.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.commondomain.model.BreedModel
import com.app.dogs.ui.Screen
import com.app.dogs.ui.feature.breeds.BreedsScreen
import com.app.dogs.ui.feature.favorite.FavoriteScreen
import com.app.dogs.ui.feature.home.HomeTabItem.Breeds
import com.app.dogs.ui.feature.home.HomeTabItem.Watchlist
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeScreen(
    navigateToDetails: (BreedModel) -> Unit,
    navigateToComparison: (BreedModel, BreedModel) -> Unit = { _, _ -> }
) {
    composable(Screen.Home.routeName) {
        val viewModel: HomeViewModel = hiltViewModel()
        val vmState by viewModel.uiState.collectAsStateWithLifecycle()
        HomeView(
            vmState = vmState,
            onPageChange = viewModel::onPageChange,
            navigateToDetails = navigateToDetails,
            navigateToComparison = navigateToComparison,
        )
    }
}


