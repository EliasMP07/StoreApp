package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.core.presentation.designsystem.TicketShape
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction
import com.devdroid07.storeapp.store.presentation.pay.FinishPayState
import com.devdroid07.storeapp.store.presentation.pay.component.utils.getCurrentFormattedDate

@Composable
fun TicketSuccessPay(
    state: FinishPayState,
    onAction: (FinishPayAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF46D19E)),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(300.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .clip(
                        TicketShape(
                            20.dp,
                            CornerSize(0)
                        )
                    )
                    .animateContentSize(),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 40.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.thanks_for_you),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF46D19E)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.price,
                            state.totalPrice
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(
                            id = R.string.date_and_time,
                            getCurrentFormattedDate()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF46D19E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = CheckIcon,
                        contentDescription = stringResource(R.string.content_description_success_pay),
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        StoreActionButtonOutline(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.btn_go_to_orders),
            textColor = MaterialTheme.colorScheme.background,
            borderColor = MaterialTheme.colorScheme.background,
            isLoading = false
        ) {
            onAction(FinishPayAction.OnNavigateHomeClick)
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}