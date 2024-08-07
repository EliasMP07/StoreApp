package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressLogoIndicator(
    modifier: Modifier = Modifier,
    background: Color,
    content: @Composable() (BoxScope.() -> Unit),
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
