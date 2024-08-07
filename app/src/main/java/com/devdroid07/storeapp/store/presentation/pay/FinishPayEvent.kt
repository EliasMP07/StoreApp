package com.devdroid07.storeapp.store.presentation.pay

sealed interface FinishPayEvent {
    data object Success: FinishPayEvent
}