package com.app.dogs.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.dogs.ui.feature.comparison.comparisonScreen
import com.app.dogs.ui.feature.comparison.navigateToComparisonView
import com.app.dogs.ui.feature.details.detailsScreen
import com.app.dogs.ui.feature.details.navigateToDetailsView
import com.app.dogs.ui.feature.home.homeScreen

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
