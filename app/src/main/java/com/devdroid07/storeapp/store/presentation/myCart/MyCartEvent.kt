package com.devdroid07.storeapp.store.presentation.myCart

import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.SnackBarStyle
import com.devdroid07.storeapp.core.presentation.ui.UiText

interface MyCartEvent {
    data class Success(val message: UiText,  val snackBarStyle: SnackBarStyle): MyCartEvent
    data class Error(val error: UiText): MyCartEvent
}