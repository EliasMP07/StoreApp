package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.StoreIconButtonBack
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonFavorite
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.shimmerEffect
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailAction
import com.devdroid07.storeapp.store.presentation.productDetail.SelectableItemCard
import com.devdroid07.storeapp.store.presentation.productDetail.StarRating

@Composable
fun ProductDetailShimmerEffect(
    paddingValues: PaddingValues
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .shimmerEffect()
                .fillMaxWidth()
        )
        ElevatedCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(50.dp),
            shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                    ) {
                        Box(modifier = Modifier
                            .height(20.dp)
                            .width(100.dp)
                            .shimmerEffect())
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(modifier = Modifier
                            .height(20.dp)
                            .width(200.dp)
                            .shimmerEffect())
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(150.dp)
                            .clip(CircleShape)
                            .shimmerEffect())
                        Box(modifier = Modifier
                            .height(10.dp)
                            .width(100.dp)
                            .shimmerEffect())
                    }
                }
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(70.dp)
                        .shimmerEffect())
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .shimmerEffect())
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier
                            .height(10.dp)
                            .width(30.dp)
                            .shimmerEffect())
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(modifier = Modifier
                            .height(20.dp)
                            .width(50.dp)
                            .shimmerEffect())
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shimmerEffect())
                }
            }
        }
    }
}