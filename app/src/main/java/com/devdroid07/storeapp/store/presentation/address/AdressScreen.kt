@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.address

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.ui.util.isVisibleBottomSheet
import com.devdroid07.storeapp.store.presentation.address.components.BottomSheetAddAddress
import com.devdroid07.storeapp.store.presentation.address.components.ItemAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddressScreenRoot(
    context: Context,
    viewModel: AddressViewModel,
    navigateToPayment: (Int) -> Unit,
    navigateToUpdateAddress: (String) -> Unit,
    onBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )
    )

    BackHandler {
        if (scaffoldState.isVisibleBottomSheet) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        } else {
            onBack()
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AddressEvent.Error -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(event.error.asString(context))
                }
            }
            is AddressEvent.Success -> {
                scope.launch {
                    if (scaffoldState.isVisibleBottomSheet) {
                        scaffoldState.bottomSheetState.hide()
                    }
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }

            }
        }
    }

    AddressScreen(
        state = state,
        scope = scope,
        scaffoldState = scaffoldState,
        onAction = { action ->
            when (action) {
                AddressAction.OnBackClick -> onBack()
                is AddressAction.OnPaymentClick -> navigateToPayment(action.addressId)
                is AddressAction.OnUpdateAddressClick -> navigateToUpdateAddress(action.addressId)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )

}

@Composable
fun AddressScreen(
    state: AddressState,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    onAction: (AddressAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.adress_title_screen),
                openDrawer = {},
                onBack = {
                    onAction(AddressAction.OnBackClick)
                },
                isMenu = false,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState)
        },
        sheetContent = {
            BottomSheetAddAddress(
                state = state,
                onAction = onAction,
                spacing = spacing
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val result = handleResultView(
                isLoading = state.isLoading,
                contentLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = state.error,
                errorContent = {
                    ErrorContent(
                        error = it,
                        onRetry = { onAction(AddressAction.OnRetryClick) })
                }
            )
            if (result) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            horizontal = spacing.spaceMedium,
                            vertical = spacing.spaceMedium
                        )
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
                ) {
                    items(
                        state.addressList,
                        key = { it.id }
                    ) {address ->
                        ItemAddress(
                            address = address,
                            spacing = spacing,
                            onClick = {
                                onAction(AddressAction.OnPaymentClick(it))
                            },
                            onDeleteClick = {
                                onAction(AddressAction.OnDeleteAddress(addressId = it))
                            },
                            onEditClick = {
                                onAction(AddressAction.OnUpdateAddressClick(it))
                            }
                        )
                    }
                    item {
                        StoreActionButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.btn_text_add_address),
                            isLoading = false
                        ) {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                }
            }

        }
    }
}