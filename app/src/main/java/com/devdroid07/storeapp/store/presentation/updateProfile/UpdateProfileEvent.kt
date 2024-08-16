package com.devdroid07.storeapp.store.presentation.updateProfile

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface UpdateProfileEvent {
    data object Success: UpdateProfileEvent
    data class Error(val error: UiText): UpdateProfileEvent
}