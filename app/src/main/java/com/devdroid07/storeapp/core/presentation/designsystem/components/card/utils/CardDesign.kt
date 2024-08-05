package com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class CardDesign(
    @DrawableRes val logo: Int?,
    @ColorInt val color: Color,
    @ColorInt val textColor: Color,
)