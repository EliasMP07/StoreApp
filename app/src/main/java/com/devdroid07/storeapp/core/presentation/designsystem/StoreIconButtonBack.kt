package com.devdroid07.storeapp.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StoreIconButtonBack(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = "Favorite item",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}