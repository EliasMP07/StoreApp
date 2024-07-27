@file:OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)

package com.devdroid07.storeapp.store.presentation.productDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.StoreIconButtonBack
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonFavorite
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateAttention
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.presentation.productDetail.components.BottomSheetContent
import com.devdroid07.storeapp.store.presentation.productDetail.components.ProductDetailShimmerEffect

@Composable
fun ProductDetailRootScreenRoot(
    state: ProductDetailState,
    context: Context,
    viewModel: ProductDetailViewModel,
    onAction: (ProductDetailAction) -> Unit,
    onBack: () -> Unit
) {

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ProductDetailEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is ProductDetailEvent.Success -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    ProductDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ProductDetailAction.OnBackClick -> onBack()
                else -> Unit
            }
            onAction(action)
        }
    )
}

@Composable
private fun handleResultProduct(
    isLoading: Boolean,
    error: UiText?,
    retry: () -> Unit,
    paddingValues: PaddingValues
): Boolean {
    return when {
        isLoading -> {
            ProductDetailShimmerEffect(
                paddingValues = paddingValues
            )
            false
        }

        error != null -> {
            ErrorContent(
                error = error,
                onRetry = retry
            )
            false
        }

        else -> true
    }

}

@Composable
private fun ProductDetailScreen(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit
) {
    Scaffold{
        val result = handleResultProduct(
            isLoading = state.isLoading,
            error = state.error,
            retry = { /*TODO*/ },
            paddingValues = it
        )
        if (result) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    GlideImage(
                        modifier = Modifier
                            .animateAttention()
                            .align(Alignment.Center),
                        model = state.product.image,
                        contentDescription = "ImageProduct"
                    )
                    this@Column.AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        visible = !state.product.isFavorite,
                        exit = scaleOut(),
                        enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
                    ) {
                        StoreIconButtonFavorite(
                            isFavorite = false,
                            onClick = {
                                onAction(ProductDetailAction.AddFavoriteClick(state.product.id.toString()))
                            }
                        )
                    }
                    this@Column.AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        visible = state.product.isFavorite,
                        exit = scaleOut(),
                        enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
                    ) {
                        StoreIconButtonFavorite(
                            isFavorite = true,
                            onClick = {
                                onAction(ProductDetailAction.RemoveFavoriteClick(state.product.id.toString()))
                            }
                        )
                    }
                    StoreIconButtonBack(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp),
                        onClick = {
                            onAction(ProductDetailAction.OnBackClick)
                        }
                    )
                }
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(50.dp),
                    shape = RoundedCornerShape(
                        topEnd = 25.dp,
                        topStart = 25.dp
                    ),
                ) {
                    BottomSheetContent(
                        state = state,
                        onAction = onAction
                    )
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
    onAdd: () -> Unit = {},
    onRemove: () -> Unit = {},
    quantity: Int = 1,
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.3f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onAdd) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add item"
            )
        }
        Text(text = quantity.toString())
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "Remove item"
            )
        }
    }
}


@Composable
fun StarRating(
    rating: Double = 0.0,
    valueReview: String? = null,
    maxRating: Int = 5,
    onRatingChanged: (Double) -> Unit
) {
    var currentRating by remember { mutableStateOf(rating) }

    LaunchedEffect(rating) {
        currentRating = rating
    }

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
                    i - 0.5 <= currentRating -> Icons.Filled.StarHalf
                    else -> Icons.Filled.StarBorder
                }
                val tint = when {
                    i <= currentRating -> Color.Yellow
                    i - 0.5 <= currentRating -> Color.Yellow
                    else -> Color.Gray
                }

                Icon(
                    imageVector = icon,
                    contentDescription = "Star Rating",
                    tint = tint,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            currentRating = i.toDouble()
                            onRatingChanged(currentRating)
                        }
                )
            }
        }
        if (valueReview != null) {
            Text(
                text = "($valueReview Reviews)",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}