@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.StoreIconButtonBack
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonFavorite
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreScaffold

@Composable
fun ProductDetailRootScreenRoot(
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    ProductDetailScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ProductDetailScreen(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit
) {
    StoreScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    model = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                    contentDescription = "ImageProduct"
                )
                StoreIconButtonFavorite(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    isFavorite = false,
                    onClick = {

                    }
                )
                StoreIconButtonBack(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    onClick = {

                    }
                )
            }
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
                            Text(
                                text = "Roller Rabbit",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            StarRating(
                                rating = 2.96f,
                                valueReview = "320"
                            ) {

                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            SelectableItemCard()
                            Text(
                                text = "Avaliable in stock",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "USB 3.0 and USB 2.0 Compatibility Fast data transfers Improve PC Performance High Capacity; Compatibility Formatted NTFS for Windows 10, Windows 8.1, Windows 7; Reformatting may be required for other operating systems; Compatibility may vary depending on user’s hardware configuration and operating system",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "Total price",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.5f
                                    )
                                )
                            )
                            Text(text = "$198.00", style = MaterialTheme.typography.titleLarge)
                        }
                        StoreActionButton(
                            modifier = Modifier.weight(1f),
                            text = "Add to Card", isLoading = false,
                            icon = Icons.Rounded.ShoppingCart
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductDetailRootScreenPreview() {
    StoreAppTheme {
        ProductDetailScreen(
            state = ProductDetailState(),
            onAction = {}
        )
    }
}


@Composable
fun SelectableItemCard(
    item: String = "1"
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.3f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add item")
        }
        Text(text = item)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.Remove, contentDescription = "Remove item")
        }
    }
}


@Composable
fun StarRating(
    rating: Float = 0.0f,
    valueReview: String = "",
    maxRating: Int = 5,
    onRatingChanged: (Float) -> Unit
) {
    var currentRating by remember { mutableFloatStateOf(rating) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 1..maxRating) {
                val icon = when {
                    i <= currentRating -> Icons.Filled.Star
                    i - 0.5f <= currentRating -> Icons.Filled.StarHalf
                    else -> Icons.Filled.StarBorder
                }
                val tint = when {
                    i <= currentRating -> Color.Yellow
                    i - 0.5f <= currentRating -> Color.Yellow
                    else -> Color.Gray
                }

                Icon(
                    imageVector = icon,
                    contentDescription = "Star Rating",
                    tint = tint,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            currentRating = i.toFloat()
                            onRatingChanged(currentRating)
                        }
                )
            }
        }
        Text(text = "($valueReview Review)", style = MaterialTheme.typography.labelMedium)
    }
}
