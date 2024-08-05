package com.devdroid07.storeapp.store.presentation.payment

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface PaymentEvent {
    data class Success(val message: UiText): PaymentEvent
    data class Error(val error: UiText): PaymentEvent
}