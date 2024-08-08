
package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction

@Composable
fun ErrorPay(
    onAction: (FinishPayAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = {
                onAction(FinishPayAction.OnBackClick)
            }) {
                Icon(
                    imageVector = CloseIcon,
                    contentDescription = stringResource(id = R.string.content_description_navigationBack),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.img_error_payment),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.failure_progresspay_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background
        )
        Text(
            text = stringResource(id = R.string.failure_progresspay_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background
        )
        Spacer(modifier = Modifier.weight(1f))
        StoreActionButtonOutline(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.btn_change_other_methot_ay),
            borderColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.background,
            isLoading = false
        ) {
            onAction(FinishPayAction.OnBackClick)
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}
