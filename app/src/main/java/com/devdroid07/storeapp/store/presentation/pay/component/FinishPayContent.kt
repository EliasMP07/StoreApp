package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction
import com.devdroid07.storeapp.store.presentation.pay.FinishPayState
import com.devdroid07.storeapp.store.presentation.pay.ItemConfirmAddress
import com.devdroid07.storeapp.store.presentation.payment.components.ItemCard

@Composable
fun FinishPayContent(
    modifier: Modifier,
    state: FinishPayState,
    spacing: Dimensions,
    onAction: (FinishPayAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Productos",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(
                    id = R.string.price,
                    state.totalPrice
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        HorizontalDivider()
        Text(
            text = "Direccion de entrega",
            style = MaterialTheme.typography.titleMedium
        )
        ItemConfirmAddress(address = state.address)
        HorizontalDivider()
        Text(
            text = "Metodo de pago",
            style = MaterialTheme.typography.titleMedium
        )
        ItemCard(
            card = state.card,
            spacing = spacing
        ) {

        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pagas",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(
                    id = R.string.price,
                    state.totalPrice
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        StoreActionButton(
            text = "Confirmar compra",
            isLoading = false
        ) {
            onAction(FinishPayAction.OnPayClick)
        }
    }
}