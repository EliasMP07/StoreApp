@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.payment

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreSnackBar
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.SnackBarStyle
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isVisibleBottomSheet
import com.devdroid07.storeapp.store.presentation.payment.components.BottomSheetAddCard
import com.devdroid07.storeapp.store.presentation.payment.components.ItemCard
import kotlinx.coroutines.launch

@Composable
fun PaymentScreenRoot(
    context: Context,
    viewModel: PaymentViewModel,
    onBack: () -> Unit,
    navigateToFinishPay: (String, String, String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

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
            is PaymentEvent.Error -> {
                focusManager.clearFocus()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.error.asString(context)
                    )
                }
            }
            is PaymentEvent.Success -> {
                focusManager.clearFocus()
                if (scaffoldState.isVisibleBottomSheet) {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = SnackBarStyle.SuccessCreateCard.type
                        )
                    }
                }
            }
            is PaymentEvent.SuccessCreateToken -> {
                navigateToFinishPay(
                    event.addressId,
                    event.cardId,
                    event.tokenId
                )
            }
        }

    }

    PaymentScreen(
        state = state,
        scaffoldState = scaffoldState,
        onAction = { action ->
            when (action) {
                PaymentAction.OnAddCardClick -> {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
                PaymentAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun PaymentScreen(
    state: PaymentState,
    scaffoldState: BottomSheetScaffoldState,
    onAction: (PaymentAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    BottomSheetScaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.payment_title_screen),
                isMenu = false,
                onBack = {
                    onAction(PaymentAction.OnBackClick)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) {
                StoreSnackBar(snackbarData = it)
            }
        },
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = 30.dp,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetAddCard(
                state = state,
                spacing = spacing,
                onAction = onAction
            )
        }
    ) { paddingValues ->

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
                    onRetry = {
                        onAction(PaymentAction.OnRetryClick)
                    }
                )
            }
        )
        if (result) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(spacing.spaceMedium)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(
                        state.cards,
                        key = { it.id }) { card ->
                        ItemCard(
                            card = card,
                            spacing = spacing,
                            onClick = {
                                onAction(
                                    PaymentAction.OnCardSelectedClick(
                                        it
                                    )
                                )
                            }
                        )
                    }
                    item {
                        StoreActionButton(
                            modifier = Modifier.padding(top = spacing.spaceMedium),
                            text = stringResource(R.string.text_btn_add_card),
                            isLoading = false
                        ) {
                            onAction(PaymentAction.OnAddCardClick)
                        }
                    }
                }
            }
        }
    }
    if (state.isGetTokenId) {
        CircularLoading()
    }
}





