@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.store.domain.model.Banner

@Composable
fun BannerItem(
    banner: Banner
){
    ElevatedCard(
        modifier = Modifier.aspectRatio(16f/7f),
        shape = RoundedCornerShape(20.dp),
        onClick = {
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier,
                contentScale = ContentScale.FillBounds,
                model = banner.imageUrl, contentDescription = null
            )
        }
    }
}
