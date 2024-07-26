package com.devdroid07.storeapp.store.presentation.favorite

import com.devdroid07.storeapp.store.presentation.home.HomeAction

sealed interface FavoriteAction {
    data object OnBackClick : FavoriteAction
    data object RetryClick: FavoriteAction
    data class OnProductDetailClick(val idProduct: String): FavoriteAction
    data class RemoveFavoriteSlide(val productId: String): FavoriteAction
}