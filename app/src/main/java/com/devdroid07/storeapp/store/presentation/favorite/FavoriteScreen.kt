@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.favorite

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.SwipeToDeleteContainer
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.store.presentation.favorite.component.FavoriteShimmerEffect
import com.devdroid07.storeapp.store.presentation.favorite.component.ItemFavorite
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreenRoot(
    context: Context,
    state: FavoriteState,
    openDrawer: () -> Unit,
    viewModel: FavoriteViewModel,
    navigateBack: () -> Unit,
    navigateDetailProduct: (String) -> Unit,
    onAction: (FavoriteAction) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is FavoriteEvent.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(event.error.asString(context))
                }
            }
            is FavoriteEvent.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }
    FavoriteScreen(
        state = state,
        onAction = { action ->
            when (action) {
                FavoriteAction.OpenDrawer -> openDrawer()
                FavoriteAction.OnBackClick -> navigateBack()
                is FavoriteAction.OnProductDetailClick -> navigateDetailProduct(action.idProduct)
                else -> Unit
            }
            onAction(action)
        },
        snackbarHostState = snackbarHostState
    )

}

@Composable
private fun FavoriteScreen(
    state: FavoriteState,
    onAction: (FavoriteAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.my_favorite),
                isMenu = true,
                openDrawer = {
                    onAction(FavoriteAction.OpenDrawer)
                }
            )
        }
    ) { paddingValue ->

        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                FavoriteShimmerEffect(
                    modifier = Modifier.fillMaxSize(),
                    paddingValues = paddingValue
                )
            },
            isEmpty = state.productFavorites.isEmpty(),
            contentEmpty = {
                EmptyListScreen(
                    text = stringResource(id = R.string.empty_favorite),
                    image = R.drawable.empty_data
                )
            },
            error = state.error,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = {
                        onAction(FavoriteAction.RetryClick)
                    }
                )
            }
        )

        if (result) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.productFavorites) { product ->
                        SwipeToDeleteContainer(
                            item = product,
                            onDelete = {
                                onAction(FavoriteAction.RemoveFavoriteSlide(it.id.toString()))
                            }
                        ) {
                            ItemFavorite(
                                product = product,
                                onDetailProduct = { onAction(FavoriteAction.OnProductDetailClick(it)) })
                        }
                    }
                }

            }
        }

    }
}