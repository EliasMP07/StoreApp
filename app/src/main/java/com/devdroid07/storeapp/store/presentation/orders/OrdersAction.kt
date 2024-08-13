package com.devdroid07.storeapp.store.presentation.orders

sealed interface OrdersAction {
    data object OpenDrawerClick: OrdersAction
    data class OnDetailOrderClick(val orderId: String): OrdersAction
    data object OnRetry: OrdersAction
}