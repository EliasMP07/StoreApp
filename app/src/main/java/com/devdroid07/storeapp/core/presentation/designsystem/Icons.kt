package com.devdroid07.storeapp.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.devdroid07.storeapp.R

val Logo : ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.logo_splashscreen)

val GoogleIcon : ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.google_icon)

