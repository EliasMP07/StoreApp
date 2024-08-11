@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateAttention
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnterFromLeft
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnterRight
import com.devdroid07.storeapp.store.domain.model.Order
import dashedBorder

@Composable
fun ItemOrder(
    spacing: Dimensions,
    order: Order
){
    Card(
        modifier = Modifier
            .fillMaxWidth().animateEnterRight().padding(spacing.spaceSmall),
        onClick = {

        },
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            GlideImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .dashedBorder(
                        color = Color(0xFF562F0C),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
                ,
                model = order.products.first().image,
                contentDescription = stringResource(R.string.content_description_img_product)
            )
            Column(
                modifier = Modifier.weight(0.6f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (order.status == "PAGADO") "Pediente" else order.status,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = stringResource(id = R.string.order_number, order.id),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.price,
                        order.products.sumOf {
                            it.price
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.Top),
                text = order.timestamp,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}
