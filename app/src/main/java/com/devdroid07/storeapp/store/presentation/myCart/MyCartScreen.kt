@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.myCart

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreSnackBar
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isVisibleBottomSheet
import com.devdroid07.storeapp.store.presentation.myCart.components.BottomSheetMyCart
import com.devdroid07.storeapp.store.presentation.myCart.components.FooterMyCart
import com.devdroid07.storeapp.store.presentation.myCart.components.MyCartContent
import kotlinx.coroutines.launch


@Composable
fun MyCartScreenRoot(
    context: Context,
    navigateToPay: () -> Unit,
    viewModel: MyCartViewModel,
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

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is MyCartEvent.Error -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.error.asString(context)
                    )
                }
            }
            is MyCartEvent.Success -> {
                scope.launch {
                    if (scaffoldState.isVisibleBottomSheet) {
                        scaffoldState.bottomSheetState.hide()
                    }
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Indefinite,
                        actionLabel = event.snackBarStyle.type
                    )
                    //Se valida el resultado del snackbar si hay un accion o si se oculto haga otra opcion
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onAction(MyCartAction.OnRestoreRemove)
                        }
                        SnackbarResult.Dismissed -> {
                            viewModel.onAction(MyCartAction.OnConfirmRemove)
                        }
                    }
                }
            }
        }

    }

    MyCartScreen(
        state = state,
        scaffoldState = scaffoldState,
        onAction = { action ->
            when (action) {
                MyCartAction.OnBackClick -> onBack()
                MyCartAction.OnContinuePayClick -> navigateToPay()
                MyCartAction.OnCartClick -> {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}


@Composable
private fun MyCartScreen(
    state: MyCartState,
    scaffoldState: BottomSheetScaffoldState,
    onAction: (MyCartAction) -> Unit,
) {
    BottomSheetScaffold(
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = 30.dp,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) {
                StoreSnackBar(
                    snackbarData = it,
                    labelButton = state.labelButton
                )
            }
        },
        sheetContent = {
            BottomSheetMyCart(
                state = state,
                onAction = onAction
            )
        },
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.my_cart_title_screen),
                isMenu = false,
                onBack = {
                    onAction(MyCartAction.OnBackClick)
                }
            )
        }
    ) { paddingValue ->

        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            isEmpty = state.myCart.isEmpty(),
            contentEmpty = {
                EmptyListScreen(
                    modifier = Modifier.fillMaxSize(),
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
                MyCartContent(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f),
                    state = state,
                    onAction = onAction
                )
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


