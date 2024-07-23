@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.auth.presentation.register.components

import androidx.compose.foundation.background
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
import com.bumptech.glide.integration.compose.placeholder
import com.devdroid07.storeapp.R
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
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            modifier = Modifier
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = image,
            contentDescription = "",
            transition = CrossFade
        )
        if (image.isBlank()){
            Icon(
                imageVector = CamaraIcon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}