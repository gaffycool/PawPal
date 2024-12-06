package com.app.presentation

sealed class Screen(val routeName: String) {
    data object Home : Screen("Home")
    data object Detail : Screen("Detail")
    data object Comparison : Screen("Comparison")
}
