package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.TicketShapePay
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction
import com.devdroid07.storeapp.store.presentation.pay.FinishPayState
import com.devdroid07.storeapp.store.presentation.payment.components.ItemCard

@Preview
@Composable
fun FinishPayContent(
    modifier: Modifier = Modifier,
    state: FinishPayState = FinishPayState(),
    spacing: Dimensions = Dimensions(),
    onAction: (FinishPayAction) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .background(Color.White)
            .padding(spacing.spaceSmall)
            .shadow(
                2.dp,
                shape = TicketShapePay(
                    8f,
                    4f
                ),
                clip = true
            )
            .fillMaxWidth(),
        contentPadding = PaddingValues(spacing.spaceMedium),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                text = stringResource(R.string.title_tickepay),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(
            state.listCart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${it.quantity} ",
                    style = MaterialTheme.typography.bodyMedium
                )
                val cutText = if (it.title.length >= 25) {
                    25
                } else {
                    it.title.length
                }
                Text(
                    text = it.title.substring(
                        0,
                        cutText
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        id = R.string.price,
                        it.price.toDouble() * it.quantity
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.spaceMedium)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
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
                text = stringResource(R.string.delivery_address),
                style = MaterialTheme.typography.titleMedium
            )
            ItemConfirmAddress(address = state.address)
            HorizontalDivider()
            Text(
                text = stringResource(R.string.payment_method),
                style = MaterialTheme.typography.titleMedium
            )
            ItemCard(
                card = state.card,
                spacing = spacing
            ) {
                onAction(FinishPayAction.GoToChangePaymentMethodClick)
            }
            Text(
                text = stringResource(R.string.change_other_pay_aux_text),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        0.5f
                    )
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.spaceMedium)
            ) {
                Text(
                    text = stringResource(R.string.pays),
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
            StoreActionButton(
                text = stringResource(R.string.btn_text_confirm_pay),
                isLoading = false
            ) {
                onAction(FinishPayAction.OnPayClick)
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
    }

}