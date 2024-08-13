package com.devdroid07.storeapp.store.presentation.orderDetail

sealed interface OrderDetailAction {
    data object OnBackClick: OrderDetailAction
    data object OnRetry: OrderDetailAction
    data class OnProductDetailClick(val idProduct: String): OrderDetailAction
}