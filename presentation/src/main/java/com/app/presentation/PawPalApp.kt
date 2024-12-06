package com.app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.presentation.feature.comparison.comparisonScreen
import com.app.presentation.feature.comparison.navigateToComparisonView
import com.app.presentation.feature.details.detailsScreen
import com.app.presentation.feature.details.navigateToDetailsView
import com.app.presentation.feature.home.homeScreen

@Composable
fun PawPalApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.routeName) {
        homeScreen(
            navigateToDetails = navController::navigateToDetailsView,
            navigateToComparison = navController::navigateToComparisonView
        )
        detailsScreen(onBackClick = navController::popBackStack)
        comparisonScreen(onBackClick = navController::popBackStack)
    }
}
