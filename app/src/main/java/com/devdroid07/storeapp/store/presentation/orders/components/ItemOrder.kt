package com.devdroid07.storeapp.store.presentation.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import coil.compose.SubcomposeAsyncImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateEnterRight
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorImageLoad
import com.devdroid07.storeapp.store.domain.model.Order
import com.devdroid07.storeapp.store.presentation.orderDetail.components.utils.StatusOrder
import dashedBorder

@Composable
fun ItemOrder(
    spacing: Dimensions,
    order: Order,
    onClick: (String) -> Unit
){
    val name = StatusOrder.fromString(order.status)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateEnterRight(
                durationMillis = 1000
            )
            .padding(spacing.spaceSmall),
        onClick = {
            onClick(order.id.toString())
        },
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            SubcomposeAsyncImage(
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
                loading = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect())
                },
                error = {
                    ErrorImageLoad()
                },
                model = order.products.first().image,
                contentDescription = stringResource(R.string.content_description_img_product)
            )
            Column(
                modifier = Modifier.weight(0.4f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = name.title),
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
                        order.transactionAmount
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.Top),
                text = order.timestamp,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

    }
}
