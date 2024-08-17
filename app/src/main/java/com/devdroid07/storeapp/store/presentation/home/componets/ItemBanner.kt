package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorImageLoad
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
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = banner.imageUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            loading = {
                Box(
                    modifier = Modifier.aspectRatio(16f / 7f).shimmerEffect()
                )
            },
            error = {
                ErrorImageLoad()
            }
        )
    }
}
