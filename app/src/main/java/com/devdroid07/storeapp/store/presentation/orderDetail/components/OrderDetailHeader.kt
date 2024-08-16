package com.devdroid07.storeapp.store.presentation.orderDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateEnterTop
import com.devdroid07.storeapp.store.presentation.orderDetail.OrderDetailState

@Composable
fun OrderDetailHeader(
    modifier: Modifier = Modifier,
    state: OrderDetailState,
) {
    Row(
        modifier = modifier
            .animateEnterTop()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(
                    id = R.string.order_number,
                    state.order.id
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Text(
                text = state.order.timestamp,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Text(
                text = stringResource(R.string.products_pay),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(
                id = R.string.total_order,
                state.order.transactionAmount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}