package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.shimmerEffect

@Composable
fun ShimmerListProductItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isLoading){
        Card(
            modifier = modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .width(150.dp)
                    .height(10.dp).shimmerEffect())
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .width(120.dp)
                    .height(10.dp).shimmerEffect())
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .width(100.dp)
                    .height(10.dp).shimmerEffect())
            }
        }
    }else{
        contentAfterLoading()
    }
}
