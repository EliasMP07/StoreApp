package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.store.domain.model.Address

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