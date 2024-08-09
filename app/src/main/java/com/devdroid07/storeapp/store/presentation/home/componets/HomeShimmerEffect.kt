package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.shimmerEffect

@Composable
fun HomeShimmerEffect(
    paddingValues: PaddingValues,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(20.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            Column {
                Box(
                    modifier = Modifier
                        .aspectRatio(16f / 7f)
                        .clip(RoundedCornerShape(20.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            Column {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(100.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        items(4) {
            Card(
                modifier = Modifier.padding(5.dp)
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
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(10.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(10.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(10.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}