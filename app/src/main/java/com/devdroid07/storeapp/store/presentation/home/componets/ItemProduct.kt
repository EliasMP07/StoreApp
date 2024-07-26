package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonFavorite
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateAttention
import com.devdroid07.storeapp.store.domain.model.Product

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemProduct(
    product: Product,
    addFavorite :(String) -> Unit ={},
    removeFavorite :(String) -> Unit ={},
    onClick: (idProduct: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateAttention(),
        onClick = {
            onClick(product.id.toString())
        }
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(180.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    model = product.image,
                    contentDescription = null
                )
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            this@Card.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                visible = !product.isFavorite,
                exit = scaleOut(),
                enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
            ) {
                StoreIconButtonFavorite(
                    isFavorite = false,
                    onClick = {
                        addFavorite(product.id.toString())
                    }
                )
            }
            this@Card.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                visible = product.isFavorite,
                exit = scaleOut(),
                enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
            ) {
                StoreIconButtonFavorite(
                    isFavorite = true,
                    onClick = {
                        removeFavorite(product.id.toString())
                    }
                )
            }

        }
    }
}