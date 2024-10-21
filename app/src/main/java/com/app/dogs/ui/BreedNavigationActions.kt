package com.app.dogs.ui

sealed class Screen(val routeName: String) {
    data object Home : Screen("Home")
    data object Detail : Screen("Detail")
}