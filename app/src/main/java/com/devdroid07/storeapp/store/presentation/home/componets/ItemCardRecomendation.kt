package com.devdroid07.storeapp.store.presentation.home.componets

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.StarRating
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateOffset
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil
import com.devdroid07.storeapp.store.domain.model.Product

@Composable
fun ItemCardRecommended(
    spacing: Dimensions,
    product: Product,
    onProductClick: (Product) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 20.dp),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onProductClick(product)
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
                contentLoading = {
                    Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)).shimmerEffect())
                },
                contentDescription =  R.string.content_description_img_product
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.price, product.price),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    StarRating(
                        rating = product.ratingRate,
                    ) {
                    }
                }
            }
            ButtonAnimation()
        }
    }
}

@Composable
private fun ButtonAnimation() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.animateOffset(),
            imageVector = Icons.Rounded.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background
        )
    }
}

