package com.devdroid07.storeapp.store.presentation.payment

import com.devdroid07.storeapp.store.domain.model.Card

sealed interface PaymentAction {
    data object OnBackClick: PaymentAction
    data object OnCreateCardClick: PaymentAction
    data object OnAddCardClick: PaymentAction
    data object OnRetryClick: PaymentAction
    data class OnCardSelectedClick(val card: Card): PaymentAction
}