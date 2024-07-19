package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.presentation.productDetail.SelectableItemCard
import com.devdroid07.storeapp.store.presentation.productDetail.StarRating

@Composable
fun BottomSheetContent(
    product: Product,
    isExpanded: Boolean,
    onExpandedClick: () -> Unit
) {
    var cutText by remember(product.description) { mutableStateOf<String?>(null) }

    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(product.description, isExpanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = 5 - 1
        if (!isExpanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = product.description.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Justify
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                StarRating(
                    rating = product.ratingRate,
                    valueReview = product.ratingCount.toString()
                ) {

                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SelectableItemCard()
                Text(
                    text = "Available in stock",
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
            Box {
                Text(
                    text = cutText ?: product.description,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Justify
                    ),
                    onTextLayout = { textLayoutResultState.value = it },
                )
                if (!isExpanded) {
                    val density = LocalDensity.current
                    Text(
                        "... See more",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Bold,
                        ),
                        onTextLayout = { seeMoreSizeState.value = it.size },
                        modifier = Modifier
                            .then(
                                if (seeMoreOffset != null)
                                    Modifier.offset(
                                        x = with(density) { seeMoreOffset.x.toDp() },
                                        y = with(density) { seeMoreOffset.y.toDp() },
                                    )
                                else
                                    Modifier
                            )
                            .clickable {
                                onExpandedClick()
                                cutText = null
                            }
                            .alpha(if (seeMoreOffset != null) 1f else 0f)
                    )
                }
            }
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
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            StoreActionButton(
                modifier = Modifier.weight(1f),
                text = "Add to Cart",
                isLoading = false,
                icon = Icons.Rounded.ShoppingCart
            ) {

            }
        }
    }
}