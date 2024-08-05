package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    rating: Double = 0.0,
    valueReview: String? = null,
    isClickable: Boolean = true,
    onClickReview: () -> Unit = {},
    iconSize: Dp = 24.dp,
    maxRating: Int = 5,
    onRatingChanged: (Double) -> Unit = {}
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
                        .size(iconSize)
                        .clickable(
                            enabled = isClickable
                        ) {
                            currentRating = i.toDouble()
                            onRatingChanged(currentRating)
                        }
                )
            }
        }
        if (valueReview != null) {
            Text(
                modifier = Modifier.clickable { onClickReview() },
                text = "($valueReview Reviews)",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}