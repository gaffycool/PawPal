package com.app.presentation.feature.breeds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.commondomain.model.BreedModel
import com.app.presentation.R
import com.app.presentation.component.AutoCompleteText
import com.app.presentation.component.BreedItemView
import com.app.presentation.component.text.TextHeadlineMediumPrimary
import com.app.presentation.theme.AppTheme
import com.app.presentation.theme.space4

@Composable
fun BreedsScreen(
    refreshData: Boolean = false,
    navigateToDetails: (BreedModel) -> Unit,
    navigateToComparison: (BreedModel, BreedModel) -> Unit
) {
    val viewModel: BreedsViewModel = hiltViewModel()
    val vmState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(refreshData) {
        viewModel.fetchBreeds()
    }
    BreedsView(
        vmState = vmState,
        actionFavorite = viewModel::actionFavorite,
        onBreedSelected = {
            if (vmState.enableComparison)
                viewModel.selectBreed(it)
            else {
                viewModel.onValueChange("")
                navigateToDetails(it)
            }
        },
        onValueChange = viewModel::onValueChange,
        onDismissMenu = viewModel::onDismissMenu,
        showSearchMenu = viewModel::showSearchMenu,
        enableComparison = viewModel::enableComparison,
        compareBreeds = {
            viewModel.compareBreeds { first, second ->
                navigateToComparison(first, second)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun BreedsView(
    @PreviewParameter(BreedViewParameterProvider::class) vmState: BreedsViewState,
    actionFavorite: (Int) -> Unit = {},
    onBreedSelected: (BreedModel) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onDismissMenu: () -> Unit = {},
    compareBreeds: () -> Unit = {},
    showSearchMenu: (Boolean) -> Unit = {},
    enableComparison: () -> Unit = {},
) {
    AppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(space4),
            topBar = {
                TopAppBar(
                    title = { TextHeadlineMediumPrimary(stringResource(R.string.tab_home_title)) },
                    actions = {
                        if (!vmState.enableComparison) {
                            IconButton(onClick = { showSearchMenu(true) }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = ""
                                )
                            }
                        }
                        IconButton(onClick = enableComparison) {
                            Icon(
                                painter = painterResource(R.drawable.ic_filter_list),
                                contentDescription = ""
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                AnimatedVisibility(vmState.enableComparison) {
                    ExtendedFloatingActionButton(
                        onClick = compareBreeds,
                        icon = { Icon(Icons.Filled.Check, "Extended floating action button.") },
                        text = { Text(text = "Compare") },
                    )
                }
            }
        ) { padding ->
            Column(Modifier.padding(padding)) {
                AnimatedVisibility(vmState.showSearchBar) {
                    AutoCompleteText(
                        label = R.string.search_hint,
                        showDropDown = vmState.showDropdownMenu,
                        filterOpts = vmState.searchedBreeds(),
                        onValueChange = onValueChange,
                        onItemSelect = onBreedSelected,
                        onDismissMenu = {
                            showSearchMenu(false)
                            onDismissMenu()
                        }
                    )
                }
                AnimatedVisibility(vmState.enableComparison) {
                    Spacer(modifier = Modifier.height(space4))
                    Text("Select 2 breeds to compare side by side")
                }
                Spacer(modifier = Modifier.height(space4))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(space4)) {
                    itemsIndexed(items = vmState.breeds) { index, breed ->
                        BreedItemView(
                            model = breed,
                            actionFavorite = { actionFavorite(index) },
                            onClick = { onBreedSelected(breed) },
                            isSelected = vmState.isSelected(breed)
                        )
                    }
                }
            }
        }
    }
}


class BreedViewParameterProvider : PreviewParameterProvider<BreedsViewState> {
    override val values = sequenceOf(
        BreedsViewState(
            breeds = listOf(
                BreedModel("Affenpinscher", "", false),
                BreedModel("Afghan Hound", "", false),
                BreedModel("Airedale Terrier", "", false),
                BreedModel("Akita", "", false),
                BreedModel("Affenpinscher", "", false),
                BreedModel("Afghan Hound", "", false),
                BreedModel("Airedale Terrier", "", false),
                BreedModel("Akita", "", false),
            ),
            showSearchBar = true,
            enableComparison = true
        )
    )
}
