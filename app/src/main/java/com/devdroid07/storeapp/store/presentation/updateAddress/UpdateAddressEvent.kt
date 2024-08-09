package com.devdroid07.storeapp.store.presentation.updateAddress

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface UpdateAddressEvent {
    data object Success: UpdateAddressEvent
    data class Error(val error: UiText): UpdateAddressEvent
}