package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil
import com.devdroid07.storeapp.store.domain.model.Product

@Composable
fun ItemProduct(
    product: Product,
    onClick: (idProduct: String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onClick(product.id.toString())
        },
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadImageCoil(
                modifier = Modifier
                    .width(180.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                model = product.image,
                contentScale = ContentScale.Fit,
                contentDescription = R.string.content_description_img_product,
                contentLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .shimmerEffect()
                    )
                }
            )
            Text(
                text = product.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = product.category,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(
                    id = R.string.price,
                    product.price
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}