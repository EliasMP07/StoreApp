package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.ErrorImageIcon

@Composable
fun ErrorImageLoad(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ErrorImageIcon,
            contentDescription = stringResource(id = R.string.content_description_error_image)
        )
    }
}