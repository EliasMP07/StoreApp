@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.pay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.presentation.pay.component.DialogProgressPay
import com.devdroid07.storeapp.store.presentation.pay.component.FinishPayContent
import com.devdroid07.storeapp.store.presentation.payment.components.ItemCard

@Composable
fun FinishPayScreenRoot(
    viewModel: FinishPayViewModel,
    navigateToPayment: () -> Unit,
    navigateToHome: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            FinishPayEvent.Success -> {
                navigateToHome()
            }
        }
    }
    FinishPayScreen(
        state = state,
        onAction = { action ->
            when (action) {
                FinishPayAction.OnBackClick -> onBack()
                FinishPayAction.OnChangeOtherCardClick -> navigateToPayment()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun FinishPayScreen(
    state: FinishPayState,
    onAction: (FinishPayAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            StoreToolbar(
                title = "Confirmar compra",
                openDrawer = {},
                onBack = {
                    onAction(FinishPayAction.OnBackClick)
                },
                isProfile = false,
                isMenu = false
            )
        }
    ) { paddingValue ->
        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            error = state.error,
            retry = {
                onAction(FinishPayAction.OnRetryClick)
            }
        )
        if (result){
            FinishPayContent(
                modifier = Modifier.padding(paddingValue),
                state = state,
                spacing = spacing,
                onAction = onAction
            )
        }

        if (state.isPaying) {
            DialogProgressPay(
                progress = state.pay,
                onAction = onAction
            )
        }
    }
}

@Composable
fun ItemConfirmAddress(
    address: Address,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null
        )
        Text(
            text = stringResource(
                id = R.string.item_adreess_template,
                address.postalCode,
                address.state,
                address.mayoralty
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = address.phoneNumber)
    }
}