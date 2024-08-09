package com.devdroid07.storeapp.store.presentation.home

sealed interface HomeAction {
    data class OnProductDetailClick(val idProduct: String): HomeAction
    data object OnSearchClick: HomeAction
    data object RetryClick: HomeAction
    data object OnMyCartClick: HomeAction
    data object OnAccountClick: HomeAction
}