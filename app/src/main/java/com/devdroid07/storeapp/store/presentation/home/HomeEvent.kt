package com.devdroid07.storeapp.store.presentation.home

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface HomeEvent {
    data class Success(val message: UiText): HomeEvent
    data class Error(val error: UiText): HomeEvent
}