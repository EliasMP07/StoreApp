package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

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