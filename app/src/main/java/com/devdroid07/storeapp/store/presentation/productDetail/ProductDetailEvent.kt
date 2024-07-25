package com.devdroid07.storeapp.store.presentation.productDetail

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface ProductDetailEvent {
    data class Success(val message: UiText): ProductDetailEvent
    data class Error(val error: UiText): ProductDetailEvent
}