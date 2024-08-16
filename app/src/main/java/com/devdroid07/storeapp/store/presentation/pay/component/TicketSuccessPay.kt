package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.TicketShapePay
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateEnterBottom
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction
import com.devdroid07.storeapp.store.presentation.pay.FinishPayState
import com.devdroid07.storeapp.store.presentation.pay.component.utils.getCurrentFormattedDate

/**
 * Pantalla cuando el pago fue existoso
 *
 * @param modifier Modificador para cambiar el composable
 * @param state Estado de la Ui
 * @param spacing Espacios definidos para evitar usar valores si no los que ya se tienen definido
 * @param onAction Acciones de la pantalla
 */
@Composable
fun TicketSuccessPay(
    modifier: Modifier = Modifier,
    state: FinishPayState,
    spacing: Dimensions,
    onAction: (FinishPayAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF46D19E))
    ) {
        Printer(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        LazyColumn(
            modifier = modifier
                .animateEnterBottom(
                    initialOffsetY = 350f,
                    durationMillis = 2800
                )
                .padding(spacing.spaceLarge)
                .padding(top = spacing.spaceLarge)
                .clip(
                    TicketShapePay(
                        8f,
                        4f
                    )
                )
                .background(color = Color.White)
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
                Text(
                    text = stringResource(R.string.thanks_for_you),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Text(
                    text = getCurrentFormattedDate(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.5f
                        )
                    )
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
                ItemCardPay(
                    card = state.card,
                    spacing = spacing
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.spaceMedium)
                ) {
                    Text(
                        text = stringResource(R.string.total_pay),
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
                    modifier = Modifier.padding(horizontal = spacing.spaceMedium),
                    text = stringResource(R.string.btn_go_to_home),
                    isLoading = false
                ) {
                    onAction(FinishPayAction.OnNavigateHomeClick)
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
        ) {
            IconButton(onClick = {
                onAction(FinishPayAction.OnNavigateHomeClick)
            }) {
                Icon(
                    imageVector = CloseIcon,
                    contentDescription = stringResource(R.string.content_description_navigationBack),
                    tint = Color.White
                )
            }
        }
    }

}