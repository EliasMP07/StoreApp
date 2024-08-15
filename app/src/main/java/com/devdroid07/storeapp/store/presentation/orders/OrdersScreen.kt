@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.orders

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.orders.components.ItemOrder

@Composable
fun OrderScreenRoot(
    viewModel: OrdersViewModel,
    drawerState: DrawerState,
    closeDrawer: () -> Unit,
    openDrawer: () -> Unit,
    navigateToDetailOrder: (String) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        if (drawerState.isOpen) {
            closeDrawer()
        } else {
            onBack()
        }
    }
    OrderScreen(
        state = state,
        onAction = { action ->
            when (action) {
                OrdersAction.OpenDrawerClick -> openDrawer()
                is OrdersAction.OnDetailOrderClick -> navigateToDetailOrder(action.orderId)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun OrderScreen(
    state: OrdersState,
    onAction: (OrdersAction) -> Unit,
) {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.orders_title_screen),
                isMenu = true,
                openDrawer = {
                    onAction(OrdersAction.OpenDrawerClick)
                }
            )
        }
    ) { paddingValues ->
        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            contentEmpty = {
                EmptyListScreen(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.empty_orders),
                    image = R.drawable.empty_data
                )
            },
            errorContent = {
                ErrorContent(error = it, onRetry = {onAction(OrdersAction.OnRetry)})
            },
            isEmpty = state.ordersList.isEmpty(),
            error = state.error
        )
        if(result){
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(state.ordersList) {
                    ItemOrder(
                        spacing = spacing,
                        order = it,
                        onClick = {idOrder ->
                            onAction(OrdersAction.OnDetailOrderClick(idOrder))
                        }
                    )
                }
            }
        }

    }
}