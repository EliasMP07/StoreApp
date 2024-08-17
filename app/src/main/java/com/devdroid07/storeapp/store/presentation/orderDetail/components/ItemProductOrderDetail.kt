package com.devdroid07.storeapp.store.presentation.orderDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateEnterRight
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil
import com.devdroid07.storeapp.store.domain.model.Product

@Composable
fun ItemProductOrderDetail(
    spacing: Dimensions,
    product: Product,
    onProductClick: (String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.animateEnterRight(),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onProductClick(product.id.toString())
        }
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(spacing.spaceSmall)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImageCoil(
                modifier = Modifier.size(80.dp),
                model = product.image,
                contentDescription = R.string.content_description_img_product,
                contentLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                },
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.spaceSmall)
            ) {
                Text(
                    text = product.title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            }
        }
    }
}