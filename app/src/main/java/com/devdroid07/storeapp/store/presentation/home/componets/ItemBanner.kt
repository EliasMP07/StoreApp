package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil
import com.devdroid07.storeapp.store.domain.model.Banner

@Composable
fun BannerItem(
    banner: Banner,
) {
    ElevatedCard(
        modifier = Modifier.aspectRatio(16f / 7f),
        shape = RoundedCornerShape(20.dp),
        onClick = {
        }
    ) {
        LoadImageCoil(
            modifier = Modifier.fillMaxSize(),
            model = banner.imageUrl,
            contentScale = ContentScale.FillBounds,
            contentLoading = {
                Box(
                    modifier = Modifier
                        .aspectRatio(16f / 7f)
                        .shimmerEffect()
                )
            },
            contentDescription = R.string.image_banner
        )
    }
}