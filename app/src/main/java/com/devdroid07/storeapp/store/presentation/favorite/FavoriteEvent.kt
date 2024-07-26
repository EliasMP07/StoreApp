package com.devdroid07.storeapp.store.presentation.favorite

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface FavoriteEvent {

    data class Success(val message: UiText) : FavoriteEvent

    data class Error(val error: UiText) : FavoriteEvent

}