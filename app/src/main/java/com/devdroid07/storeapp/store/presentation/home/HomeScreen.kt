@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreScaffold
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isScrolled
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.store.presentation.home.componets.HeaderHome
import com.devdroid07.storeapp.store.presentation.home.componets.HomeShimmerEffect
import com.devdroid07.storeapp.store.presentation.home.componets.ItemCardRecomendation
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct

@Composable
fun HomeScreenRoot(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    HomeScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
private fun handleResult(
    isLoading: Boolean,
    error: String?,
    retry: () -> Unit,
    paddingValues: PaddingValues
): Boolean {
    return when {
        isLoading -> {
            HomeShimmerEffect(
                paddingValues = paddingValues
            )
            false
        }

        error != null -> {
            ErrorContent(error = error, onRetry = retry)
            false
        }

        else -> true
    }
}


@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val lazyGridState = rememberLazyGridState()
    StoreScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topAppBar = {
            StoreToolbar(
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (state.error == null) {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    text = {
                        Text(
                            text = stringResource(R.string.my_cart),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.ShoppingCart,
                            contentDescription = "Icono de agregar nueva materia",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {

                    },
                    expanded = lazyGridState.isScrolled()
                )
            }
        }
    ) {
        val result = handleResult(
            isLoading = state.isLoading,
            paddingValues = it,
            error = state.error,
            retry = {
                onAction(HomeAction.RetryClick)
            }
        )
        if (result) {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    HeaderHome(product = state.productRecomended, onSearchClick = {

                    }, onProductClick = {
                        onAction(HomeAction.OnProductDetailScreen(it.id.toString()))
                    })
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(text = "All products", style = MaterialTheme.typography.titleMedium)
                }
                items(state.products, key ={it.id}) { product ->
                    ItemProduct(
                        product = product,
                        onClick = { idProduct ->
                            onAction(HomeAction.OnProductDetailScreen(idProduct))
                        }
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    StoreAppTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {},
        )
    }
}