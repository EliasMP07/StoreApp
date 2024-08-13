@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.orderDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.orderDetail.components.ItemProductOrderDetail
import com.devdroid07.storeapp.store.presentation.orderDetail.components.OrderDetailHeader
import com.devdroid07.storeapp.store.presentation.orderDetail.components.OrderStatusProgress
import com.devdroid07.storeapp.store.presentation.orderDetail.components.utils.StatusOrder

@Composable
fun OrderDetailScreenRoot(
    viewModel: OrderDetailViewModel,
    navigateToDetailProduct: (String) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    OrderDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                OrderDetailAction.OnBackClick -> onBack()
                is OrderDetailAction.OnProductDetailClick -> navigateToDetailProduct(action.idProduct)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun OrderDetailScreen(
    state: OrderDetailState,
    onAction: (OrderDetailAction) -> Unit,
) {
    val spacing = LocalSpacing.current
    Scaffold(topBar = {
        StoreToolbar(
            title = stringResource(R.string.title_screen_tacker_order),
            isMenu = false,
            onBack = {
                onAction(OrderDetailAction.OnBackClick)
            },
        )
    }) { paddingValues ->
        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = { onAction(OrderDetailAction.OnRetry) })
            },
            error = state.error
        )
        if (result) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(spacing.spaceSmall),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
            ) {
                item {
                    OrderDetailHeader(state = state)
                }
                items(
                    state.order.products
                ) {
                    ItemProductOrderDetail(
                        spacing = spacing,
                        product = it
                    ) {productId ->
                        onAction(OrderDetailAction.OnProductDetailClick(productId))
                    }
                }
                item {
                    OrderStatusProgress(currentStatus = StatusOrder.fromString(state.order.status))
                }
            }
        }

    }
}

@Preview
@Composable
private fun OrderDetailScreenPreview() {
    StoreAppTheme {
        OrderDetailScreen(
            state = OrderDetailState(),
            onAction = {}
        )
    }
}
