package com.devdroid07.storeapp.store.presentation.address.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnter
import com.devdroid07.storeapp.store.domain.model.Address

@Composable
fun ItemAddress(
    address: Address,
    spacing: Dimensions,
) {
    Card(
        modifier = Modifier.animateEnter(),
        shape = RoundedCornerShape(spacing.spaceLarge),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onBackground
        ),
        onClick = { /*TODO*/ })
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
        ) {
            Text(
                text = address.street,
                style = MaterialTheme.typography.titleMedium
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
            Text(
                text = stringResource(id = R.string.phone_template, address.phoneNumber),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}