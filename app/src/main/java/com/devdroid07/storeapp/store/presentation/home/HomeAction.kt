package com.devdroid07.storeapp.store.presentation.home

sealed interface HomeAction {
    data class OnProductDetailClick(val idProduct: String): HomeAction
    data object OnSearchClick: HomeAction
    data object RetryClick: HomeAction
    data object OnMyCartClick: HomeAction
    data object OnAccountClick: HomeAction
    data class AddFavoriteClick(val productId: String): HomeAction
    data class RemoveFavoriteClick(val productId: String): HomeAction
}