package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R

@Composable
fun EmptyMyCartScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.empty_cart),
            contentDescription = null
        )
        Text(
            text = "Tu carrito esta vacio",
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}