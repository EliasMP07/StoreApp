@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnter
import com.devdroid07.storeapp.store.domain.model.Cart

@Composable
fun ItemCard(
    cart: Cart
) {
    var isExpand by rememberSaveable {
        mutableStateOf(false)
    }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(20.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .animateEnter()
            .animateContentSize(),
        onClick = { /*TODO*/ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        bottomStart = 30.dp
                    )
                )
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
                model = cart.image,
                contentScale = ContentScale.Fit,
                contentDescription = ""
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cart.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = cart.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${(cart.price.toDouble() * cart.quantity)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = cart.quantity.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
        HorizontalDivider()
    }
}