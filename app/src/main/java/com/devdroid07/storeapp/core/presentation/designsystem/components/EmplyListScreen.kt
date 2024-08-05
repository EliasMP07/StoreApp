package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListScreen(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes  image: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = image),
            contentDescription = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}