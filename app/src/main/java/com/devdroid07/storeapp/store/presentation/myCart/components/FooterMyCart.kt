package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.ui.util.roundToTwoDecimals
import com.devdroid07.storeapp.store.presentation.myCart.MyCartAction
import com.devdroid07.storeapp.store.presentation.myCart.MyCartState

@Composable
fun FooterMyCart(
    state: MyCartState,
    onAction: (MyCartAction) -> Unit,
    modifier: Modifier,
) {
    ElevatedCard(
        shape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp
        ),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = stringResource(
                            R.string.total_items_products,
                            state.myCart.size
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(R.string.sub_total),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    Text(
                        text = stringResource(
                            id = R.string.price,
                            state.totalSale.roundToTwoDecimals()
                        ),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = stringResource(
                            id = R.string.price,
                            state.totalSale.roundToTwoDecimals()
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            StoreActionButton(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = stringResource(R.string.btn_text_continue_pay),
                isLoading = false
            ) {
                onAction(MyCartAction.OnContinuePayClick)
            }
        }
    }
}