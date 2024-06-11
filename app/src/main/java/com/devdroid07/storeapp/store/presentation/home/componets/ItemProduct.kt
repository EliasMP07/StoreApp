package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.shimmerEffect

@OptIn(ExperimentalGlideComposeApi::class)
@Preview
@Composable
fun ItemProduct(
    isFavorite: Boolean = false
) {
    Card(
        modifier = Modifier.padding(5.dp)
    ) {
        Box(
        ){
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(180.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Blue),
                    model = R.drawable.server_down_image, contentDescription = null
                )
                Text(text = "Maison Margiela", style = MaterialTheme.typography.titleMedium)
                Text(text = "Replica Sneakers", style = MaterialTheme.typography.bodyMedium)
                Text(text = "$550", style = MaterialTheme.typography.titleMedium)
            }
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorite item",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}