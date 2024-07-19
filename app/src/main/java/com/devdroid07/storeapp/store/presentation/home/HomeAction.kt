package com.devdroid07.storeapp.store.presentation.home

sealed interface HomeAction {
    data class OnProductDetailScreen(val idProduct: String): HomeAction
    data object RetryClick: HomeAction
}