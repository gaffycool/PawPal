package com.app.presentation.feature.home

import androidx.annotation.StringRes
import com.app.presentation.R
import com.app.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor() : BaseViewModel<HomeViewState>() {

    override fun defaultState(): HomeViewState = HomeViewState()

    fun onPageChange(index: Int) {
        newState { it.copy(selectedPage = index) }
    }
}

data class HomeViewState(
    val selectedPage: Int = 0,
    val tabs: List<HomeTabItem> = listOf(HomeTabItem.Breeds, HomeTabItem.Watchlist)
)

enum class HomeTabItem(@StringRes val title: Int) {
    Breeds(R.string.tab_home_title),
    Watchlist(R.string.tab_watchlist_title),
}
