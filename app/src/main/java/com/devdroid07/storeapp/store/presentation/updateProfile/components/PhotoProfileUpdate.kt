package com.devdroid07.storeapp.store.presentation.updateProfile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CamaraIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator

@Composable
fun PhotoProfileUpdate(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    isClickeable: Boolean = true,
    onClick: () -> Unit = {},
    image: String = "",
) {
    Box(modifier = modifier.size(110.dp)) {
        Box(
            modifier = Modifier
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
                }
        ) {
            LoadImageCoil(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                contentScale = ContentScale.Crop,
                model = image.ifBlank { R.drawable.ic_account },
                contentDescription = R.string.content_description_profile,
                contentLoading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        BallSpinFadeLoaderProgressIndicator(
                            diameter = 40.dp
                        )
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = CamaraIcon,
                contentDescription = null,
            )
        }
    }
}