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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
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
    size: Dp = 100.dp,
    isClickeable: Boolean = true,
    onClick: () -> Unit = {},
    image: String = ""
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(
                BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.onBackground
                ),
                CircleShape
            )
            .clickable(
                enabled = isClickeable
            ) {
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
            contentDescription = stringResource(id = R.string.content_description_profile),
            transition = CrossFade,
            failure = placeholder(R.drawable.error_image)
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