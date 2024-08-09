package com.devdroid07.storeapp.store.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct
import com.devdroid07.storeapp.store.presentation.search.components.SearchTextField

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel,
    navigateToDetailProduct: (String) -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SearchAction.OnProductDetailClick -> navigateToDetailProduct(action.idProduct)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {

        SearchTextField(
            text = state.query,
            onValueChange = {
                onAction(SearchAction.OnChangeQuery(it))
            },
            shouldShowHint = false,
            onSearch = {
                onAction(SearchAction.OnSearch)
            }
        )

        val result = handleResultView(
            isLoading = state.isSearching,
            contentLoading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            isEmpty = state.isEmpty,
            contentEmpty = {
                EmptyListScreen(
                    text = stringResource(id = R.string.products_search_empty),
                    image = R.drawable.empty_data
                )
            },
            error = state.error,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = {
                        onAction(SearchAction.OnRetry)
                    })
            }
        )
        if (result) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = spacing.spaceSmall),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                items(
                    state.products,
                    key = { it.id }) { product ->
                    ItemProduct(product = product) {
                        onAction(SearchAction.OnProductDetailClick(it))
                    }
                }
            }
        }

    }
}