@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.auth.presentation.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.core.presentation.designsystem.CamaraIcon

@Composable
fun PhotoProfile(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    image: String = ""
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(
                BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.onBackground
                ),
                CircleShape
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            contentScale = ContentScale.Crop,
            model = image,
            contentDescription = "",
            transition = CrossFade
        )
        if (image.isBlank()) {
            Icon(
                imageVector = CamaraIcon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}