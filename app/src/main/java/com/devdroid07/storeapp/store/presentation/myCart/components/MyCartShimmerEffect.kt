package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun MyCartShimmerEffect(
    paddingValues: PaddingValues
) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            items(5) {
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
            item(

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )
                        )
                        .shimmerEffect()
                )
            }
    }
}