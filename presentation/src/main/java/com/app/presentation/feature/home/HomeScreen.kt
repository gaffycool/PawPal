package com.app.presentation.feature.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.commondomain.model.BreedModel
import com.app.presentation.Screen

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


