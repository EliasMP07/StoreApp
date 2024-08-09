@file:OptIn(ExperimentalMaterial3Api::class,
            ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.myCart

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.devdroid07.storeapp.store.presentation.myCart.components.FooterMyCart
import com.devdroid07.storeapp.store.presentation.myCart.components.ItemCart
import com.devdroid07.storeapp.store.presentation.myCart.components.MyCartShimmerEffect
import kotlinx.coroutines.launch

@Composable
fun MyCartScreenRoot(
    state: MyCartState,
    context: Context,
    navigateToPay: () -> Unit,
    viewModel: MyCartViewModel,
    navigateBack: () -> Unit,
    onAction: (MyCartAction) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is MyCartEvent.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = event.error.asString(context))
                }
            }
            is MyCartEvent.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = event.message.asString(context))
                }
            }
        }

    }

    MyCartScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = { action ->
            when (action) {
                MyCartAction.OnBackClick -> navigateBack()
                MyCartAction.OnContinuePayClick -> navigateToPay()
                else -> Unit
            }
            onAction(action)
        }
    )

}

@Composable
private fun MyCartScreen(
    state: MyCartState,
    snackbarHostState: SnackbarHostState,
    onAction: (MyCartAction) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.my_cart_title_screen),
                isMenu = false,
                onBack = {
                    onAction(MyCartAction.OnBackClick)
                },
                openDrawer = { /*TODO*/ }
            )
        }
    ) { paddingValue ->

        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                MyCartShimmerEffect(paddingValues = paddingValue)
            },
            isEmpty = state.myCart.isEmpty(),
            contentEmpty = {
                EmptyListScreen(
                    text = stringResource(id = R.string.cart_empty),
                    image = R.drawable.empty_cart
                )
            },
            error = state.error,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = {
                        onAction(MyCartAction.OnRetryClick)
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
                        .padding(horizontal = 10.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.myCart) { cart ->
                        SwipeToDeleteContainer(
                            item = cart,
                            onDelete = {
                                onAction(MyCartAction.OnRemoveProduct(it.idProduct.toInt()))
                            }
                        ) {
                            ItemCart(
                                cart = cart
                            )
                        }
                    }
                }

                FooterMyCart(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                )

            }
        }

    }
}