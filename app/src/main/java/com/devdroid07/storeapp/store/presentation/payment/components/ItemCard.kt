package com.devdroid07.storeapp.store.presentation.payment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.ArrowLeftIcon
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.CardIssuer
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.IssuerFinder
import com.devdroid07.storeapp.store.domain.model.Card

@Composable
fun ItemCard(
    card: Card,
    spacing: Dimensions,
    onClick: (Card) -> Unit
) {

    val cardIssuer = when (IssuerFinder.detect(card.cardNumber)){
        CardIssuer.VISA -> R.drawable.visa
        CardIssuer.MASTERCARD -> R.drawable.ic_mastercard
        CardIssuer.OTHER -> null
        CardIssuer.EMPTY -> null
    }
    Card(
        onClick = {
            onClick(card)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            cardIssuer?.let {
                Image(
                    modifier = Modifier.size(70.dp),
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Text(
                text = "**** ${card.cardNumber.takeLast(4)}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ArrowLeftIcon,
                contentDescription = null
            )
        }
        HorizontalDivider()
    }
}