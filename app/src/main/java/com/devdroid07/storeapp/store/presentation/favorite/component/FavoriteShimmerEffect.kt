package com.devdroid07.storeapp.store.presentation.favorite.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.shimmerEffect

@Composable
fun FavoriteShimmerEffect(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = modifier
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        items(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}