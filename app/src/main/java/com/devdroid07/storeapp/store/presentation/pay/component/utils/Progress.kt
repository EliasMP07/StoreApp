package com.devdroid07.storeapp.store.presentation.pay.component.utils

import androidx.annotation.StringRes

data class Progress(
    val hasButton: Boolean,
    @StringRes val title: Int,
    @StringRes val message: Int,
)