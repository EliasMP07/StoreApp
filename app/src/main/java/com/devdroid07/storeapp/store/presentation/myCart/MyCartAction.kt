package com.devdroid07.storeapp.store.presentation.myCart

sealed interface MyCartAction {
    data object OnBackClick: MyCartAction
    data object OnContinuePayClick: MyCartAction
    data class OnRemoveProduct(val idProduct: Int): MyCartAction
    data object OnRetryClick: MyCartAction
}