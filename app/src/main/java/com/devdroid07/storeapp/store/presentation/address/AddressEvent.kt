package com.devdroid07.storeapp.store.presentation.address

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface AddressEvent {
    data class Success(val message: UiText): AddressEvent
    data class Error(val error: UiText): AddressEvent
}