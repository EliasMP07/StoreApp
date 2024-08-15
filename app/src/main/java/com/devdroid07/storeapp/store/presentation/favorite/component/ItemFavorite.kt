package com.devdroid07.storeapp.store.presentation.favorite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.store.domain.model.Product

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemFavorite(
    product: Product,
    onDetailProduct: (String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp),
        onClick = { onDetailProduct(product.id.toString()) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .padding(20.dp)
                    .weight(0.4f)
                    .clip(RoundedCornerShape(12.dp))
                    .size(80.dp)
                    .background(Color.White),
                model = product.image,
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.content_description_img_product)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}