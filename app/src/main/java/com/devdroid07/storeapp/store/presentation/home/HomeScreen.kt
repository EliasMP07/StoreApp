@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isScrolled
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.store.presentation.home.componets.HeaderHome
import com.devdroid07.storeapp.store.presentation.home.componets.HomeShimmerEffect
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct

@Composable
fun HomeScreenRoot(
    state: HomeState,
    context: Context,
    navigateToDetailProduct: (String) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToCart :() -> Unit,
    viewModel: HomeViewModel,
    openDrawer: () -> Unit,
    onAction: (HomeAction) -> Unit
) {

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is HomeEvent.Success -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

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
            onAction(action)
        }
    )
}

@Composable
private fun HomeScreen(
    openDrawer: () -> Unit,
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val lazyGridState = rememberLazyGridState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            StoreToolbar(
                openDrawer = openDrawer,
                isMenu = true,
                onAccountClick = {
                    onAction(HomeAction.OnAccountClick)
                },
                profile = state.user?.image.orEmpty(),
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
                    .padding(paddingValue)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    HeaderHome(
                        product = state.productRecomended,
                        onSearchClick = {
                            onAction(HomeAction.OnSearchClick)
                        },
                        onProductClick = {
                            onAction(HomeAction.OnProductDetailClick(it.id.toString()))
                        })
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "All products",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(
                    state.products,
                    key = { it.id }) { product ->
                    ItemProduct(
                        product = product,
                        onClick = { idProduct ->
                            onAction(HomeAction.OnProductDetailClick(idProduct))
                        },
                        addFavorite = {
                            onAction(HomeAction.AddFavoriteClick(it))
                        },
                        removeFavorite = {
                            onAction(HomeAction.RemoveFavoriteClick(it))
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