@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.myCart

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.SwipeToDeleteContainer
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.navigation.util.navigateBack
import com.devdroid07.storeapp.store.presentation.home.componets.HomeShimmerEffect
import com.devdroid07.storeapp.store.presentation.myCart.components.EmptyMyCartScreen
import com.devdroid07.storeapp.store.presentation.myCart.components.FooterMyCart
import com.devdroid07.storeapp.store.presentation.myCart.components.ItemCard
import kotlinx.coroutines.launch

@Composable
fun MyCartScreenRoot(
    state: MyCartState,
    context: Context,
    viewModel: MyCartViewModel,
    onBack: () -> Unit,
    onAction: (MyCartAction) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is MyCartEvent.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar( event.error.asString(context),)
                }
            }
            is MyCartEvent.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar( event.message.asString(context),)
                }
            }
        }

    }

    MyCartScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = { action ->
            when (action) {
                MyCartAction.OnBackClick -> onBack()
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
                isProfile = false,
                onBack = {
                    onAction(MyCartAction.OnBackClick)
                },
                openDrawer = { /*TODO*/ }
            )
        }
    ) { paddingValue ->
        val result = handleResult(
            isLoading = state.isLoading,
            error = state.error,
            isEmpty = state.myCart.isEmpty(),
            retry = { onAction(MyCartAction.OnRetryClick) },
            paddingValues = paddingValue
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
                            ItemCard(
                                cart
                            )
                        }
                    }
                }
                FooterMyCart(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                )
            }
        }
    }
}

@Composable
private fun handleResult(
    isLoading: Boolean,
    error: String?,
    isEmpty: Boolean,
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
        isEmpty -> {
            EmptyMyCartScreen()
            false
        }
        error != null -> {
            ErrorContent(
                error = error,
                onRetry = retry
            )
            false
        }

        else -> true
    }
}

@Composable
fun MyCartScreenPreview() {

}