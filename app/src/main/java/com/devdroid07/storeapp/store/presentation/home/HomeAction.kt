package com.devdroid07.storeapp.store.presentation.home

sealed interface HomeAction {
    data object OnProductDetailScreen : HomeAction
}