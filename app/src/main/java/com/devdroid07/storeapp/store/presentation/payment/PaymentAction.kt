package com.devdroid07.storeapp.store.presentation.payment

sealed interface PaymentAction {
    data object OnBackClick: PaymentAction
    data object OnCreateCardClick: PaymentAction
    data object OnAddCardClick: PaymentAction
    data object OnRetryClick: PaymentAction
    data class OnCardSelectedClick(val addressId: String, val cardId: String): PaymentAction
}