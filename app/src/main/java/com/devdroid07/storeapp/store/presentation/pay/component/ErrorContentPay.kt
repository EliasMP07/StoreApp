package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.ui.UiText

@Composable
fun ErrorContentPay(
    error: UiText,
    onHomeClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.img_error_payment),
            contentDescription = stringResource(id = R.string.content_description_error_image_pay)
        )
        Text(
            text = error.asString(),
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        StoreActionButtonOutline(
            modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.btn_go_to_home),
            isLoading = false,
            onClick = onHomeClick
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}