package com.devdroid07.storeapp.store.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct
import com.devdroid07.storeapp.store.presentation.search.components.SearchTextField

@Composable
fun SearchScreenRoot(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    SearchScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
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
            },
            onFocusChanged = {

            }
        )

        val result = handleResultView(
            isLoading = state.isSearching,
            contentLoading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
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
            retry = {}
        )
        if(result){
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.products) { product ->
                    ItemProduct(product = product) {

                    }
                }
            }
        }

    }
}