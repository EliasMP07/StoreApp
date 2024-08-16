package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateAttention

@Composable
fun ItemQuantityProduct(
    quantity: Int,
    selected: Boolean,
    opacity: Float,
) {
    val backgroundColor = if (selected) MaterialTheme.colorScheme.onBackground else Color.Transparent
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White
            )
        ) {
            append("$quantity ")
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            ) {
                append(stringResource(id = if (quantity == 1) R.string.total_item else R.string.total_items))
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor,
                RoundedCornerShape(8.dp)
            )
            .graphicsLayer(alpha = opacity)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Text(
                modifier = Modifier.animateAttention(durationMillis = 1000),
                text = annotatedString,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        } else {
            Text(
                text = quantity.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
