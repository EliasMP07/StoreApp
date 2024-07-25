@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnter
import com.devdroid07.storeapp.store.presentation.productDetail.SelectableItemCard

@Composable
fun ItemCard() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(20.dp),
        modifier = Modifier.clip(RoundedCornerShape(30.dp)).animateEnter(),
        onClick = { /*TODO*/ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlideImage(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(80.dp)
                    .background(Color.Red),
                model = R.drawable.logo_splashscreen_scale_down,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Column {
                Text(
                    text = "Modern ligh clotes",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Dress Morden",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$254",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column {
                SelectableItemCard()
            }
        }
        HorizontalDivider()
    }
}