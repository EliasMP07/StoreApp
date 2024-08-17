@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CartIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isScrolled
import com.devdroid07.storeapp.store.presentation.home.componets.BannerPager
import com.devdroid07.storeapp.store.presentation.home.componets.HomeShimmerEffect
import com.devdroid07.storeapp.store.presentation.home.componets.HomeTopBar
import com.devdroid07.storeapp.store.presentation.home.componets.ItemCardRecommended
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct

@Composable
fun HomeScreenRoot(
    navigateToDetailProduct: (String) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToCart: () -> Unit,
    viewModel: HomeViewModel,
    openDrawer: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        openDrawer = openDrawer,
        onAction = { action ->
            when (action) {
                is HomeAction.OnProductDetailClick -> {
                    navigateToDetailProduct(action.idProduct)
                }
                HomeAction.OnAccountClick -> navigateToAccount()
                HomeAction.OnSearchClick -> navigateToSearch()
                HomeAction.OnMyCartClick -> {
                    navigateToCart()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun HomeScreen(
    openDrawer: () -> Unit,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    val topAppBarState = rememberTopAppBarState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    val lazyGridState = rememberLazyGridState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                openDrawer = openDrawer,
                onAccountClick = {
                    onAction(HomeAction.OnAccountClick)
                },
                profile = state.user?.image.orEmpty(),
                onSearchClick = {
                    onAction(HomeAction.OnSearchClick)
                },
                scrollBehavior = scrollBehavior,
                spacing = spacing
            )
        },
        floatingActionButton = {
            if (state.error == null && !state.isLoading) {
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
                            imageVector = CartIcon,
                            contentDescription = stringResource(R.string.content_decription_go_to_cart),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        onAction(HomeAction.OnMyCartClick)
                    },
                    expanded = lazyGridState.isScrolled()
                )
            }
        }
    ) { paddingValue ->

        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                HomeShimmerEffect(
                    paddingValues = paddingValue
                )
            },
            error = state.error,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = {
                        onAction(HomeAction.RetryClick)
                    }
                )
            }
        )
        if (result) {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .padding(horizontal = spacing.spaceMedium),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        BannerPager(state = state.bannersList)
                    }
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    if (state.productRecommended.ratingRate != 0.0) {
                        ItemCardRecommended(
                            product = state.productRecommended,
                            spacing = spacing,
                            onProductClick = {
                                onAction(HomeAction.OnProductDetailClick(it.id.toString()))
                            })
                    }
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = stringResource(R.string.all_products),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(
                    state.productsList,
                    key = { it.id }) { product ->
                    ItemProduct(
                        product = product,
                        onClick = { idProduct ->
                            onAction(HomeAction.OnProductDetailClick(idProduct))
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
            openDrawer = {}
        )
    }
}